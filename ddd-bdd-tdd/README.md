## Update versions

    ```bash
    mvn versions:update-properties \
      -DgenerateBackupPoms=false \
      -DallowSnapshots=false \
      -DprocessProperties=true \
      -DautoLinkItems=true \
      -DignoredVersions='.*-RC.*:.*-M.*:.*-alpha.*:.*-beta.*:.*-SNAPSHOT.*'
    ```