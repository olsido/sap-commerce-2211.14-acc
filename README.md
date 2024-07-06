# Enhanced SAP Commerce 2211.14 Accelerator

This repository contains an enhanced version of the SAP Commerce 2211.14 accelerator with additional features and configurations.

# Features

- **Initial Code Generation**: Code generated using modulegen.
- **Updated .gitignore**: Updated .gitignore file for Java and Hybris specific files.
- **Audit Data and Logging**: Disabled audit data and audit logging.
- **Solr 9 Configuration**: Added configuration for Solr 9. This is required by SAP in this version but is not yet provided by SAP; it will be available in a future version.
- **Customer Support Perspective**: Added the Backoffice Customer Support perspective.

# Prerequisites

- SAPMachines 17 JDK
- SAP Commerce 2211.14 (not provided here)

# Installation

1. **Clone the Repository**

   ```sh
   git clone git@github.com:olsido/sap-commerce-2211.14-acc.git
   cd sap-commerce-2211.14-acc
   ```

 2. **Set Up Solr 9**

 Follow the provided Solr 9 configuration to set up Solr for your SAP Commerce instance.

 3. **Build the Project**

 Use the following commands to build the project:

   ```sh
   cd hybris/bin/platform
   . ./setantenv.sh
   ant clean all
   ```

4. **Start SAP Commerce**

   ```sh
   ./hybrisserver.sh
   ```

# License

This project is licensed under the MIT License.