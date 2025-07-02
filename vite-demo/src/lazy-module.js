export function showLazyMessage() {
  const messageDiv = document.createElement('div');
  messageDiv.textContent = 'This message was loaded dynamically!';
  messageDiv.style.cssText = 'margin-top: 20px; padding: 10px; background-color: #e0ffe0; border: 1px solid #a0ffa0;';
  document.querySelector('#app').appendChild(messageDiv);
  console.log('Dynamic module loaded!');
}