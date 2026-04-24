# 📱 Smart Contact Manager (SCM 2.0)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind-3.4.3-blue.svg)](https://tailwindcss.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Smart Contact Manager** is a professional, full-stack web application designed to help users manage their personal and professional contacts efficiently. Built with the robust **Spring Boot** framework and styled with **Tailwind CSS**, it offers a seamless and secure experience.

---

## 🌟 Key Features

### 👤 User Management
- **Authentication**: Secure Signup, Login, and Logout functionality.
- **Social Login**: Integration with **Google** and **GitHub** OAuth2.
- **Profile Management**: View and update user profile details.
- **Email Verification**: Secure account activation via email.

### 📇 Contact Management
- **Full CRUD**: Add, View, Update, and Delete contacts.
- **Image Upload**: Seamlessly upload contact pictures using **Cloudinary**.
- **Advanced Search**: Search contacts by Name, Email, or Phone Number.
- **Pagination**: Efficiently browse through large contact lists.
- **Favorites**: Mark important contacts as favorites.

### 🎨 Design & UI
- **Modern Interface**: Clean, responsive design built with **Thymeleaf** and **Tailwind CSS**.
- **Dark Mode**: Fully supports Dark/Light mode switching.
- **Real-time Feedback**: Interactive alerts and messages for user actions.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.2.5, Spring Security, Spring Data JPA.
- **Frontend**: Thymeleaf, Tailwind CSS, JavaScript.
- **Database**: MySQL.
- **Cloud Services**: Cloudinary (Image Storage).
- **Mailing**: Spring Mail (SMTP).
- **Containerization**: Docker & Docker Compose.

---

## ⚙️ Environment Configuration

Before running the project, you need to configure your environment variables. You can add these to `src/main/resources/application.properties` or set them as system environment variables.

### 1. Database Setup
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/scm20
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 2. Cloudinary Setup (For Images)
Create a free account at [Cloudinary](https://cloudinary.com/) and get your credentials:
```properties
cloudinary.cloud.name=YOUR_CLOUD_NAME
cloudinary.api.key=YOUR_API_KEY
cloudinary.api.secret=YOUR_API_SECRET
```

### 3. OAuth Setup (Optional)
To enable Google/GitHub login, create apps on their respective developer consoles and provide:
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET

spring.security.oauth2.client.registration.github.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_CLIENT_SECRET
```

---

## 🏃 Getting Started

### Option 1: Using Docker (Fastest)
The project includes a `docker-compose.yml` file that sets up the App, MySQL, and phpMyAdmin.

1.  **Run Docker Compose**:
    ```bash
    docker-compose up -d
    ```
2.  **Access the App**: [http://localhost:8082](http://localhost:8082)
3.  **Database Admin**: [http://localhost:8081](http://localhost:8081)

### Option 2: Local Development

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/Adarsh09675/Smart-Contact-Manager.git
    cd Smart-Contact-Manager
    ```
2.  **Build the Project**:
    ```bash
    ./mvnw clean install
    ```
3.  **Run the Application**:
    ```bash
    ./mvnw spring-boot:run
    ```
4.  **Access the App**: [http://localhost:8081](http://localhost:8081)

---

## 📸 Screenshots
*(Add your screenshots here to make it look even better!)*

| Dashboard | Contacts List | Add Contact |
| :---: | :---: | :---: |
| ![Dashboard](https://via.placeholder.com/300x200?text=Dashboard) | ![Contacts](https://via.placeholder.com/300x200?text=Contacts) | ![Add](https://via.placeholder.com/300x200?text=Add+Contact) |

---

## 🤝 Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Developed with ❤️ by [Adarsh](https://github.com/Adarsh09675)**
