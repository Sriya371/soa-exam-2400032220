# Nutrition Management Microservices

## Included projects
- `EurekaServer` — service registry, port 8761.
- `APIGateway` — gateway entry point, port 8080.
- `CrudServiceProject` — nutrition-plan CRUD API, port 8081.
- `FoodService` — food-item CRUD API, port 8082.
- `UserService` — user-profile CRUD API, port 8083.

All projects use Java 21, Maven, Spring Boot 4.1.1, and Spring Cloud 2025.1.3, matching the versions in the supplied project. Check that these versions are available in your configured Maven repositories.

## PostgreSQL databases
Create these databases before starting services: `nutrition_db`, `food_db`, `users_db`. Set environment variables `DB_USERNAME` and `DB_PASSWORD` for each service (defaults are `postgres` and `change_me`). Nutrition Plan also supports `DB_URL`. Update application properties if you use different database names or credentials.

## Run order
1. Start `EurekaServer` and open http://localhost:8761.
2. Start `CrudServiceProject`, `FoodService`, and `UserService`.
3. Start `APIGateway`.

Each folder is a separate Maven project. In Eclipse, import each folder using **File → Import → Existing Maven Projects**.

## API endpoints
### Nutrition plan (direct port 8081; through gateway port 8080)
- `POST /nutritionplan/add`
- `GET /nutritionplan/displayall`
- `GET /nutritionplan/{id}`
- `PUT /nutritionplan/update/{id}`
- `DELETE /nutritionplan/delete/{id}`

Example POST body:
```json
{
  "planName": "Balanced Plan",
  "dailyCalories": 2000,
  "weeklyCost": 75.50,
  "active": true
}
```

### Food items
- `POST /foods`
- `GET /foods`
- `GET /foods/{id}`
- `PUT /foods/{id}`
- `DELETE /foods/{id}`

Example body: `{ "name": "Oats", "category": "Breakfast", "caloriesPerServing": 150, "price": 2.5, "available": true }`

### User profiles
- `POST /users`
- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`

Example body: `{ "fullName": "Demo User", "email": "demo@example.com", "age": 22, "heightCm": 170, "weightKg": 65 }`

Gateway routes are `/nutritionplan/**`, `/foods/**`, and `/users/**`. Swagger UI for each MVC service is at `/swagger-ui/index.html` on its direct port.

## Notes
- This is a starter architecture, not a production-secure deployment. User Service stores profile data only; it does not implement login, password storage, or JWT authentication.
- Use environment variables or a local untracked configuration for credentials. Do not commit real database passwords.
- The service projects have not been executed against your local PostgreSQL/Eclipse setup here; verify Maven dependency resolution and run tests locally.
