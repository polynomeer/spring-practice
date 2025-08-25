
```sql
CREATE DATABASE springbook CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
CREATE USER 'spring'@'%' IDENTIFIED BY 'book';
GRANT ALL PRIVILEGES ON springbook.* TO 'spring'@'%';
FLUSH PRIVILEGES;
```
