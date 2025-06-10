# app/main.py
from fastapi import FastAPI, Depends, HTTPException
from sqlalchemy import create_engine, Column, Integer, String, Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, Session
from pydantic import BaseModel
from typing import Optional, List # <-- ADDED for Python 3.9 type hints
import os
import time
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

# --- Database Configuration ---
# Retrieve DB connection details from environment variables for flexibility in containerized environments.
DB_HOST = os.getenv("DB_HOST", "localhost")
DB_PORT = os.getenv("DB_PORT", "5432")
DB_USER = os.getenv("DB_USER", "user")
DB_PASSWORD = os.getenv("DB_PASSWORD", "password") # This will come from a Kubernetes Secret later
DB_NAME = os.getenv("DB_NAME", "todo_db")

DATABASE_URL = f"postgresql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}"

# SQLAlchemy setup
engine = None
SessionLocal = None
Base = declarative_base()

# --- Database Model ---
class Task(Base):
    __tablename__ = "tasks"
    id = Column(Integer, primary_key=True, index=True)
    title = Column(String, index=True)
    description = Column(String, nullable=True)
    completed = Column(Boolean, default=False)

# --- Pydantic Schemas for FastAPI Request/Response Validation ---
class TaskBase(BaseModel):
    title: str
    # CORRECTION: Use typing.Optional for Python 3.9 compatibility (equivalent to str | None in 3.10+)
    description: Optional[str] = None # <-- CORRECTED LINE
    completed: bool = False

class TaskCreate(TaskBase):
    pass

# Renamed to TaskSchema to avoid conflict with SQLAlchemy Task model
class TaskSchema(TaskBase): # <-- RENAMED CLASS
    id: int
    class Config:
        from_attributes = True # updated from orm_mode=True in newer Pydantic versions

# --- Database Connection and Dependency ---
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.on_event("startup")
async def startup_db_client():
    global engine, SessionLocal
    # Implement a retry mechanism for DB connection
    # This is crucial in containerized environments where the database might not be ready
    # as quickly as the application container starts.
    max_retries = 10
    retry_delay_seconds = 5
    for i in range(max_retries):
        try:
            logger.info(f"Attempting to connect to database at {DATABASE_URL} (Attempt {i+1}/{max_retries})...")
            engine = create_engine(DATABASE_URL)
            SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
            # Try to create tables to force a connection test
            Base.metadata.create_all(bind=engine)
            logger.info("Database connection successful and tables created/verified.")
            return
        except Exception as e:
            logger.error(f"Database connection failed: {e}")
            if i < max_retries - 1:
                logger.info(f"Retrying in {retry_delay_seconds} seconds...")
                time.sleep(retry_delay_seconds)
            else:
                logger.critical("Max database connection retries reached. Exiting.")
                # In a real application, you might want to raise an exception or exit here
                # For this example, we'll let the app start but it won't be functional.
                break
    else: # This 'else' block executes if the loop completes without a 'return'
        logger.critical("Failed to connect to the database after multiple retries.")


# --- API Endpoints ---
@app.post("/tasks/", response_model=TaskSchema) # <-- CHANGED to TaskSchema
def create_task(task: TaskCreate, db: Session = Depends(get_db)):
    # Create a new Task instance based on SQLAlchemy model
    # Use task.model_dump() instead of task.dict() for Pydantic v2+ compatibility
    db_task = Task(**task.model_dump())
    db.add(db_task)
    db.commit()
    db.refresh(db_task)
    return db_task

@app.get("/tasks/", response_model=List[TaskSchema]) # <-- CHANGED to List[TaskSchema]
def read_tasks(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    tasks = db.query(Task).offset(skip).limit(limit).all()
    return tasks

@app.get("/tasks/{task_id}", response_model=TaskSchema) # <-- CHANGED to TaskSchema
def read_task(task_id: int, db: Session = Depends(get_db)):
    task = db.query(Task).filter(Task.id == task_id).first()
    if task is None:
        raise HTTPException(status_code=404, detail="Task not found")
    return task

@app.put("/tasks/{task_id}", response_model=TaskSchema) # <-- CHANGED to TaskSchema
def update_task(task_id: int, task: TaskCreate, db: Session = Depends(get_db)):
    db_task = db.query(Task).filter(Task.id == task_id).first()
    if db_task is None:
        raise HTTPException(status_code=404, detail="Task not found")
    # Use task.model_dump(exclude_unset=True) for Pydantic v2+ compatibility
    for key, value in task.model_dump(exclude_unset=True).items(): # <-- CORRECTED LINE
        setattr(db_task, key, value)
    db.commit()
    db.refresh(db_task)
    return db_task

@app.delete("/tasks/{task_id}")
def delete_task(task_id: int, db: Session = Depends(get_db)):
    db_task = db.query(Task).filter(Task.id == task_id).first()
    if db_task is None:
        raise HTTPException(status_code=404, detail="Task not found")
    db.delete(db_task)
    db.commit()
    return {"message": "Task deleted successfully"}

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)