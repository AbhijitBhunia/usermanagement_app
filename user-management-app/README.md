# User Management Application - 3-Tier Architecture

A full-stack web application built with **Spring Boot**, **React**, and **PostgreSQL** that demonstrates a complete 3-tier architecture with user registration, authentication, and activity logging.

## 🏗️ Architecture

### Presentation Tier (Frontend)
- **React 18** - Modern UI library
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls

### Application Tier (Backend)
- **Spring Boot 3** - RESTful API framework
- **Spring Security** - Password encryption & authentication
- **Spring Data JPA** - Database ORM
- **Maven** - Build and dependency management

### Data Tier (Database)
- **PostgreSQL 15+** - Relational database
- **Activity Logging** - Automatic tracking of all user actions

---

## ✨ Features

- ✅ User Registration with validation
- ✅ Secure Login with BCrypt password encryption
- ✅ Protected Dashboard accessible only after login
- ✅ Automatic Activity Logging (registration, login, failed attempts)
- ✅ REST API with proper error handling
- ✅ Responsive UI design
- ✅ Session management with localStorage
- ✅ CORS configuration for local development

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your Ubuntu VM:

### Required Software

1. **Java 17** (OpenJDK)
2. **Maven 3.8+**
3. **Node.js 18+** and **npm 9+**
4. **PostgreSQL 15+**
5. **Git** (for version control)

---

## 🚀 Installation Guide for Ubuntu VM

### Step 1: Update System Packages

```bash
sudo apt update
sudo apt upgrade -y
```

### Step 2: Install Java 17

```bash
sudo apt install openjdk-17-jdk -y
java -version  # Verify installation
```

### Step 3: Install Maven

```bash
sudo apt install maven -y
mvn -version  # Verify installation
```

### Step 4: Install Node.js and npm

```bash
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt install -y nodejs
node -v  # Verify Node.js installation
npm -v   # Verify npm installation
```

### Step 5: Install PostgreSQL

```bash
sudo apt install postgresql postgresql-contrib -y
sudo systemctl start postgresql
sudo systemctl enable postgresql
sudo systemctl status postgresql  # Check if running
```

### Step 6: Configure PostgreSQL

```bash
# Switch to postgres user
sudo -i -u postgres

# Access PostgreSQL prompt
psql

# Inside PostgreSQL prompt, run:
CREATE DATABASE usermanagementdb;
CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE usermanagementdb TO postgres;
\q  # Exit PostgreSQL prompt

# Exit postgres user
exit
```

### Step 7: Install Git

```bash
sudo apt install git -y
git --version  # Verify installation
```

---

## 📦 Project Setup

### Download and Extract Project Files

1. Download all 3 zip files provided
2. Extract them to create the complete project structure
3. You should have this structure:

```
user-management-app/
├── backend/
├── frontend/
├── database/
├── .gitignore
└── README.md
```

---

## 🔧 Backend Setup

### 1. Navigate to Backend Directory

```bash
cd user-management-app/backend
```

### 2. Configure Database Connection

Edit `src/main/resources/application.properties` if needed:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/usermanagementdb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Note:** Change the password if you used a different one during PostgreSQL setup.

### 3. Build and Run Backend

```bash
# Clean and build the project
mvn clean install

# Run the Spring Boot application
mvn spring-boot:run
```

The backend server will start on **http://localhost:8080**

### 4. Verify Backend is Running

Open a new terminal and run:

```bash
curl http://localhost:8080/api/auth/login
# Should return 405 Method Not Allowed (expected since it's a POST endpoint)
```

---

## 🎨 Frontend Setup

### 1. Navigate to Frontend Directory

Open a **new terminal** (keep backend running) and navigate to frontend:

```bash
cd user-management-app/frontend
```

### 2. Install Dependencies

```bash
npm install
```

This will install all required packages (React, React Router, Axios, etc.)

### 3. Run Frontend

```bash
npm start
```

The React app will start on **http://localhost:3000** and automatically open in your browser.

---

## 🧪 Testing the Application

### 1. Register a New User

1. Navigate to http://localhost:3000
2. You'll be redirected to the Login page
3. Click "Register here"
4. Fill in the registration form:
   - First Name: John
   - Last Name: Doe
   - Email: john@example.com
   - Password: password123
5. Click "Register"
6. You should see success message and be redirected to login

### 2. Login

1. Enter your credentials:
   - Email: john@example.com
   - Password: password123
2. Click "Login"
3. You should be redirected to the dashboard

### 3. View Dashboard

After successful login, you'll see:
- Your user information (name, email, user ID)
- List of recent activities (registration event, login event)

### 4. Verify Database Records

Open a new terminal and run:

```bash
# Connect to PostgreSQL
sudo -u postgres psql -d usermanagementdb

# View users
SELECT * FROM users;

# View activity logs
SELECT * FROM activity_logs ORDER BY timestamp DESC;

# Exit
\q
```

You should see your registered user and all activity logs.

---

## 📊 Database Schema

### Users Table

| Column      | Type          | Description                  |
|-------------|---------------|------------------------------|
| id          | BIGSERIAL     | Primary key (auto-increment) |
| first_name  | VARCHAR(255)  | User's first name            |
| last_name   | VARCHAR(255)  | User's last name             |
| email       | VARCHAR(255)  | User's email (unique)        |
| password    | VARCHAR(255)  | Encrypted password (BCrypt)  |
| created_at  | TIMESTAMP     | Account creation timestamp   |
| updated_at  | TIMESTAMP     | Last update timestamp        |

### Activity Logs Table

| Column      | Type          | Description                     |
|-------------|---------------|---------------------------------|
| id          | BIGSERIAL     | Primary key (auto-increment)    |
| user_id     | BIGINT        | Foreign key to users table      |
| action      | VARCHAR(255)  | Action type (e.g., USER_LOGIN)  |
| details     | TEXT          | Detailed description            |
| ip_address  | VARCHAR(50)   | User's IP address (optional)    |
| timestamp   | TIMESTAMP     | When the action occurred        |

---

## 🔌 API Endpoints

### Authentication

| Method | Endpoint            | Description          | Request Body                                      |
|--------|---------------------|----------------------|---------------------------------------------------|
| POST   | /api/auth/register  | Register new user    | `{firstName, lastName, email, password}`          |
| POST   | /api/auth/login     | Login user           | `{email, password}`                               |

### User Management

| Method | Endpoint                 | Description               | Response                          |
|--------|--------------------------|---------------------------|-----------------------------------|
| GET    | /api/user/{id}           | Get user by ID            | User details                      |
| GET    | /api/user/{id}/activities| Get user activity logs    | List of activity logs             |

---

## 🐛 Troubleshooting

### Backend Issues

**Problem: Port 8080 already in use**
```bash
# Find process using port 8080
sudo lsof -i :8080

# Kill the process
sudo kill -9 <PID>
```

**Problem: Database connection failed**
- Verify PostgreSQL is running: `sudo systemctl status postgresql`
- Check database credentials in `application.properties`
- Ensure database exists: `sudo -u postgres psql -l`

**Problem: Maven build fails**
- Verify Java version: `java -version` (should be 17)
- Clear Maven cache: `mvn clean`
- Check internet connection for dependency downloads

### Frontend Issues

**Problem: npm install fails**
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and package-lock.json
rm -rf node_modules package-lock.json

# Reinstall
npm install
```

**Problem: CORS errors in browser console**
- Verify backend is running on port 8080
- Check SecurityConfig.java CORS configuration
- Clear browser cache

**Problem: API calls fail**
- Check if backend is running: `curl http://localhost:8080`
- Verify API base URL in `src/services/api.js`

### Database Issues

**Problem: Cannot connect to PostgreSQL**
```bash
# Restart PostgreSQL
sudo systemctl restart postgresql

# Check logs
sudo tail -f /var/log/postgresql/postgresql-15-main.log
```

**Problem: Tables not created**
- Verify `spring.jpa.hibernate.ddl-auto=update` in application.properties
- Check backend logs for Hibernate errors

---

## 🔄 Pushing to GitHub

### 1. Initialize Git Repository

```bash
# Navigate to project root
cd user-management-app

# Initialize Git
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit: 3-tier user management application"
```

### 2. Create GitHub Repository

1. Go to https://github.com
2. Click "New repository"
3. Name it: `user-management-app`
4. Don't initialize with README (we already have one)
5. Click "Create repository"

### 3. Push to GitHub

```bash
# Add remote repository (replace YOUR_USERNAME with your GitHub username)
git remote add origin https://github.com/YOUR_USERNAME/user-management-app.git

# Push to GitHub
git branch -M main
git push -u origin main
```

### 4. Verify on GitHub

Visit your repository: `https://github.com/YOUR_USERNAME/user-management-app`

---

## 🚀 Deploying on Another Machine

### Quick Setup from GitHub

```bash
# Clone repository
git clone https://github.com/YOUR_USERNAME/user-management-app.git
cd user-management-app

# Install prerequisites (Java, Maven, Node.js, PostgreSQL, Git)
# Follow "Installation Guide for Ubuntu VM" section above

# Setup database
sudo -u postgres psql -c "CREATE DATABASE usermanagementdb;"

# Start backend (in one terminal)
cd backend
mvn spring-boot:run

# Start frontend (in another terminal)
cd frontend
npm install
npm start
```

---

## 📁 Project Structure

```
user-management-app/
├── backend/                          # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/usermanagement/
│   │       ├── config/              # Security configuration
│   │       ├── controller/          # REST API controllers
│   │       ├── dto/                 # Data Transfer Objects
│   │       ├── model/               # JPA Entities
│   │       ├── repository/          # Database repositories
│   │       └── service/             # Business logic
│   ├── src/main/resources/
│   │   └── application.properties   # Database config
│   └── pom.xml                      # Maven dependencies
│
├── frontend/                         # React Frontend
│   ├── public/
│   │   └── index.html               # HTML template
│   ├── src/
│   │   ├── components/              # React components
│   │   ├── services/                # API service layer
│   │   ├── App.js                   # Main app component
│   │   ├── App.css                  # Styling
│   │   └── index.js                 # Entry point
│   └── package.json                 # npm dependencies
│
├── database/
│   └── setup.sql                    # Database initialization
│
├── .gitignore                       # Git ignore file
└── README.md                        # This file
```

---

## 📚 Technologies Used

| Technology       | Version | Purpose                          |
|------------------|---------|----------------------------------|
| Java             | 17      | Backend programming language     |
| Spring Boot      | 3.2.0   | Backend framework                |
| Spring Security  | 6.x     | Authentication & authorization   |
| Spring Data JPA  | 3.x     | Database ORM                     |
| PostgreSQL       | 15+     | Relational database              |
| React            | 18.2    | Frontend UI library              |
| React Router     | 6.20    | Client-side routing              |
| Axios            | 1.6     | HTTP client                      |
| Maven            | 3.8+    | Build tool                       |
| npm              | 9+      | Frontend package manager         |

---

## 🔐 Security Features

- **Password Encryption**: BCrypt hashing with salt
- **CORS Configuration**: Configured for local development
- **Input Validation**: Server-side validation for all inputs
- **Activity Logging**: All authentication events are logged
- **Session Management**: Client-side session with localStorage

---

## 📝 Development Notes

### Adding New Features

1. **New API Endpoint**: Add controller method → Service logic → Repository if needed
2. **New UI Page**: Create component in `src/components/` → Add route in `App.js`
3. **Database Changes**: Update entity classes → Spring Boot will auto-update schema

### Best Practices

- Always log activities for critical user actions
- Validate input on both frontend and backend
- Use environment variables for sensitive data in production
- Implement proper error handling
- Add unit tests for services and controllers

---

## 🎯 Learning Outcomes

After deploying this application, you will understand:

- ✅ How 3-tier architecture works
- ✅ REST API design and implementation
- ✅ Database operations with JPA
- ✅ React component structure
- ✅ Frontend-backend communication
- ✅ Authentication flow
- ✅ Activity logging patterns
- ✅ Git and GitHub workflow

---

## 📞 Support

If you encounter any issues:

1. Check the Troubleshooting section above
2. Verify all prerequisites are installed correctly
3. Check backend and frontend logs for errors
4. Ensure PostgreSQL is running and database is created
5. Verify ports 8080 and 3000 are available

---

## 📄 License

This project is open source and available for educational purposes.

---

## 🙏 Acknowledgments

- Spring Boot Documentation
- React Documentation
- PostgreSQL Documentation
- Stack Overflow Community

---

**Happy Coding! 🚀**

For detailed file descriptions, see `FILE-DESCRIPTIONS.md`
