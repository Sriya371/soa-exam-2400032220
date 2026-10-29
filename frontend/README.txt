NUTRITION MANAGEMENT - REACT FRONTEND

Requirements:
- Node.js (LTS)
- npm

Run:
1. Extract the ZIP.
2. Open a terminal inside NutritionManagementReact.
3. Install dependencies:
   npm install
4. Start the frontend:
   npm run dev
5. Open the local URL printed by Vite (usually http://localhost:5173).

Start the backend first:
- Eureka Server: 8761
- API Gateway: 8080
- CrudServiceProject: 8081
- FoodService: 8082
- UserService: 8083

The frontend calls http://localhost:8080 by default. You can change this at the top of the page.

Expected API paths:
Nutrition plans:
GET /nutritionplan/displayall
POST /nutritionplan/add
GET /nutritionplan/{id}
PUT /nutritionplan/update/{id}
DELETE /nutritionplan/delete/{id}

Food items:
GET /foods
POST /foods
GET /foods/{id}
PUT /foods/{id}
DELETE /foods/{id}

Users:
GET /users
POST /users
GET /users/{id}
PUT /users/{id}
DELETE /users/{id}

IMPORTANT:
FoodService and UserService paths are assumed standard REST mappings. If Swagger shows different paths, update apiConfig in src/App.jsx.

If browser calls fail:
- Check backend and gateway are running.
- Check gateway routes and registered service names.
- Configure CORS for http://localhost:5173 in the gateway/services.
