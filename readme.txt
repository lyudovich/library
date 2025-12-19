JSON new book
{
   "title": "Місто",
   "isbn": "978-617-12-3456-7",
   "category": {
      "id": 1
      },
   "authors": [
      { "id": 1 },
      { "id": 2 }
   ]
}

GET /api/readers
[
  {
    "id": 1,
    "name": "Іван Петренко",
    "email": "ivan.petrenko@example.com",
    "deleted": false,
    "deletedAt": null
  },
  {
    "id": 2,
    "name": "Олена Коваль",
    "email": "olena.koval@example.com",
    "deleted": true,
    "deletedAt": "2025-01-10T14:32:00"
  }
]
