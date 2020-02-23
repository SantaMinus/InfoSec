This is a training project in **Information Security**.

It checks the user's authenticity and a captcha. If everything's correct, allows him to manage files in a special directory.

**Notes**:
 - please create a folder at `F:\root` before trying it out
 - always **specify** the file **extension** with its name

The credentials for an application access are received from an **H2** database.

To **create the DB**:
1. Install H2
2. Follow the instructions in: http://www.h2database.com/html/tutorial.html#creating_new_databases

Create a `USERS` table with the following columns:

| PK | LOGIN | PASSWORD |
|----|-------|----------|
| 1  | admin | admin    |
| 2  | sava  | pass     |
| 2  | ...   | ...      |

And put some records into it. They will be used to log into the application