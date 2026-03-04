# PT2025_30222_Flita_Andrei_3



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/topics/git/add_files/#add-files-to-a-git-repository) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/pt2025_30222_flita_andrei/pt2025_30222_flita_andrei_3.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.com/pt2025_30222_flita_andrei/pt2025_30222_flita_andrei_3/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/user/project/merge_requests/auto_merge/)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing (SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***
## PT2025_30222_Flita_Andrei_3 - Orders Management System
Choose a self-explaining name for your project.

## General Structure

**Packages:**

- **bll** – Business logic (validators, CRUD operations for Client, Product, Orders)
- **dao** – Database access via generic `AbstractDAO`
- **model** – Core data classes (`Client`, `Product`, `Orders`)
- **gui** – Graphical user interface (`MainGUI`, `ClientsGUI`, `ProductsGUI`, `OrdersGUI`)
- **connection** – Manages database connection (`ConnectionFactory`)

## How It Works

1. **Startup** – The application opens with the main window (`MainGUI`), where you can access the Clients, Products, and Orders modules.

2. **Data Management** – Each module allows:
    - Adding, editing, and deleting entities
    - Viewing data in an automatically generated `JTable`

3. **Order Validation** – In the `OrdersGUI`:
    - The product stock is checked before placing an order
    - If the stock is sufficient, it is automatically reduced
    - Editing or deleting an order adjusts stock accordingly

4. **Reflection** – Used in `AbstractDAO` to dynamically create, read, and write objects from the database without duplicate code.

5. **Table Display** – `TableGenerator` dynamically creates tables using reflection to extract headers and values.
## Input & Usage

No external input is required – the graphical interface allows full control over data in the database:

- Clients: `id`, `name`, `address`
- Products: `id`, `name`, `price`, `stock`
- Orders: `id`, `client_id`, `product_id`, `quantity`, `is_completed`, `order_date`

## Output & Features

- **Data tables** for each entity, displayed automatically
- **Validation** rules:
    - Clients must have a non-empty name and address
    - Products must have a non-empty name, a positive price, and non-negative stock
    - Orders must reference valid client/product IDs and have a positive quantity
- **Automatic stock update**:
    - On order insert: stock is reduced
    - On delete: stock is restored

## Key Files

- `MainGUI.java` – Application entry point
- `ClientsGUI.java`, `ProductsGUI.java`, `OrdersGUI.java` – Windows for managing each entity
- `AbstractDAO.java` – Generic DAO for all database operations
- `TableGenerator.java` – Creates graphical tables dynamically
- `ConnectionFactory.java` – Handles database connection (MySQL)
- `Client`, `Product`, `Orders` – Data model classes

## How to Run the Application

1. **Configure the database**:
    - Database name: `ordersmanagement`
    - Tables: `Client`, `Product`, `Orders`
    - Set database credentials in `ConnectionFactory.java`

2. **Compile all `.java` files** in the project (using an IDE or terminal)

3. **Run `MainGUI.java`** to start the application

4. **Use the GUI** to manage clients, products, and orders

## Requirements

- **Java 8** or later
- IDE such as **IntelliJ IDEA** or **Eclipse**
- **MySQL** installed and configured

## Possible Improvements

- Export data to PDF or CSV
- Reporting features with filters and sorting
- User authentication and roles
- Order history and archiving

