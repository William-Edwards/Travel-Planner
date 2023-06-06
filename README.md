# Travel Planner

This is an travel itinerary generator application built with Java, Spring Boot, Thymeleaf and OpenAI's GPT-3 API. It generates detailed travel itineraries based on a user-provided destination.

## Features

- **Itinerary Generation**: Enter a destination to receive a detailed travel itinerary, including recommendations for attractions, activities, local cuisine, and lodging.
- **Destination Management**: Add, update, and delete destinations.
- **Itinerary Management**: View all generated itineraries, update them, or delete them.
- **Sample Itineraries**: View a sample of the latest itineraries.

## Installation

1. **Clone the repository**:

    ```
    git clone https://github.com/William-Edwards/Travel-Planner.git
    cd Travel-Planner
    ```

2. **Set up MySQL**:

    ![database](https://github.com/William-Edwards/Travel-Planner/blob/main/assets/travel%20planner%20db.png)
    
    Create a MySQL database and update `src/main/resources/application.properties` with your database credentials.
    
    Schema can be found in the [schema](https://github.com/William-Edwards/Travel-Planner/blob/main/travelplanner/src/main/resources/schema.sql)

3. **Set up OpenAI API**:

    Sign up for an API key at [OpenAI](https://openai.com) and create a `.env` file in the root directory of the project with the following content:

    ```
    OPENAI_KEY=your_api_key
    ```

## Running the Application

To run the application, you can use the Spring Boot Maven plugin:
```
$ mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## Usage

Visit `http://localhost:8080/` in your browser. From there, you can navigate the application to manage destinations and itineraries.

![Screenshot of the landing page](https://github.com/William-Edwards/Travel-Planner/blob/main/assets/landingpage.png)



