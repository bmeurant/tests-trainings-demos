import ruamel.yaml

yaml_file = 'docker-compose.yml'
yaml = ruamel.yaml.YAML()
yaml.preserve_quotes = True # Preserve quotes and formatting as much as possible

data = None
try:
    with open(yaml_file, 'r') as f:
        data = yaml.load(f)
except Exception as e:
    print(f"Error loading YAML file: {e}")
    exit(1)

# 1. Remove top-level 'version'
# ruamel.yaml might handle this by not writing it if not common
# Or, we can explicitly delete it if it's loaded into the dict.
if 'version' in data:
    del data['version']

# 2. Resolve dependency cycle
if 'services' in data:
    services = data['services']
    if 'otel-collector' in services and services['otel-collector'] is not None and 'depends_on' in services['otel-collector']:
        if isinstance(services['otel-collector']['depends_on'], list):
            if 'grafana' in services['otel-collector']['depends_on']:
                services['otel-collector']['depends_on'].remove('grafana')
            # If depends_on is now empty, remove it
            if not services['otel-collector']['depends_on']:
                del services['otel-collector']['depends_on']
        elif isinstance(services['otel-collector']['depends_on'], dict): # Older docker-compose versions
            if 'grafana' in services['otel-collector']['depends_on']:
                del services['otel-collector']['depends_on']['grafana']
            if not services['otel-collector']['depends_on']:
                del services['otel-collector']['depends_on']

    if 'prometheus' in services and services['prometheus'] is not None and 'depends_on' in services['prometheus']:
        if isinstance(services['prometheus']['depends_on'], list):
            if 'otel-collector' in services['prometheus']['depends_on']:
                services['prometheus']['depends_on'].remove('otel-collector')
            # If depends_on is now empty, remove it
            if not services['prometheus']['depends_on']:
                del services['prometheus']['depends_on']
        elif isinstance(services['prometheus']['depends_on'], dict): # Older docker-compose versions
             if 'otel-collector' in services['prometheus']['depends_on']:
                del services['prometheus']['depends_on']['otel-collector']
             if not services['prometheus']['depends_on']:
                del services['prometheus']['depends_on']


try:
    with open(yaml_file, 'w') as f:
        yaml.dump(data, f)
    print(f"Successfully modified {yaml_file}")
except Exception as e:
    print(f"Error writing YAML file: {e}")
    exit(1)
