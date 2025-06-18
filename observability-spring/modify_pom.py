import xml.etree.ElementTree as ET
import sys

pom_file = 'pom.xml'
otel_bom_version = '1.38.0'

try:
    ET.register_namespace('', "http://maven.apache.org/POM/4.0.0")
    tree = ET.parse(pom_file)
    root = tree.getroot()
    ns = {'mvn': 'http://maven.apache.org/POM/4.0.0'}

    # Update opentelemetry-bom version in dependencyManagement
    updated_bom = False
    for dep_mgmt_deps in root.findall('.//mvn:dependencyManagement/mvn:dependencies', ns):
        for dep_mgmt in dep_mgmt_deps.findall('mvn:dependency', ns):
            groupId = dep_mgmt.find('mvn:groupId', ns)
            artifactId = dep_mgmt.find('mvn:artifactId', ns)
            if groupId is not None and groupId.text == 'io.opentelemetry' and                artifactId is not None and artifactId.text == 'opentelemetry-bom':
                version = dep_mgmt.find('mvn:version', ns)
                if version is not None:
                    print(f"Found opentelemetry-bom, current version: {version.text}, updating to: {otel_bom_version}")
                    version.text = otel_bom_version
                    updated_bom = True
                break
        if updated_bom:
            break

    if not updated_bom:
        print(f"opentelemetry-bom not found in dependencyManagement. Adding it.")
        dep_mgmt_section = root.find('.//mvn:dependencyManagement', ns)
        if dep_mgmt_section is None: # If no dependencyManagement section, create it
            dep_mgmt_section = ET.Element('dependencyManagement')
            # Insert after properties or description, common places
            properties_el = root.find('mvn:properties', ns)
            if properties_el is not None:
                index = list(root).index(properties_el) + 1
                root.insert(index, dep_mgmt_section)
            else: # fallback append
                root.append(dep_mgmt_section)

        dependencies_el = dep_mgmt_section.find('mvn:dependencies', ns)
        if dependencies_el is None:
            dependencies_el = ET.SubElement(dep_mgmt_section, 'dependencies')

        new_bom_dep = ET.Element('dependency')
        ET.SubElement(new_bom_dep, 'groupId').text = 'io.opentelemetry'
        ET.SubElement(new_bom_dep, 'artifactId').text = 'opentelemetry-bom'
        ET.SubElement(new_bom_dep, 'version').text = otel_bom_version
        ET.SubElement(new_bom_dep, 'type').text = 'pom'
        ET.SubElement(new_bom_dep, 'scope').text = 'import'
        dependencies_el.append(new_bom_dep)
        print(f"Added opentelemetry-bom with version {otel_bom_version} to dependencyManagement.")

    # Update/Remove version for opentelemetry-spring-boot-starter in dependencies
    # Also check other io.opentelemetry artifacts for explicit versions
    for dep_section in root.findall('.//mvn:dependencies', ns): # Ensure we are not in dependencyManagement
        if dep_section.getparent().tag != '{http://maven.apache.org/POM/4.0.0}dependencyManagement':
            for dep in dep_section.findall('mvn:dependency', ns):
                groupId = dep.find('mvn:groupId', ns)
                artifactId = dep.find('mvn:artifactId', ns)

                if groupId is not None and groupId.text == 'io.opentelemetry.instrumentation' and                    artifactId is not None and artifactId.text == 'opentelemetry-spring-boot-starter':
                    version_el = dep.find('mvn:version', ns)
                    if version_el is not None:
                        print(f"Found opentelemetry-spring-boot-starter with explicit version {version_el.text}. Removing version for BOM control.")
                        dep.remove(version_el)
                    else:
                        print("Found opentelemetry-spring-boot-starter without explicit version (good).")

                elif groupId is not None and groupId.text == 'io.opentelemetry':
                    artifactId_text = artifactId.text if artifactId is not None else "N/A"
                    if artifactId_text != 'opentelemetry-bom': # Don't touch BOM if it's misplaced
                        version_el = dep.find('mvn:version', ns)
                        if version_el is not None:
                            print(f"Found {groupId.text}:{artifactId_text} with explicit version {version_el.text}. Removing for BOM control.")
                            dep.remove(version_el)

    tree.write(pom_file, encoding='utf-8', xml_declaration=True)
    print(f"Successfully updated {pom_file}")

except Exception as e:
    print(f"Error processing pom.xml: {e}")
    sys.exit(1)
