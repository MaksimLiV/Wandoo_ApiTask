Homework Assignment for Middle QA Engineer CandidatesObjective:
Your task is to develop a small backend test project for testing simple login functionality
on our loan marketplace platform webpage https://swaper.com/

Requirements (you can use any language or tool, but this is a list of preferable ones):
• Language: Java, Groovy, or Kotlin
• Test Tool: Cucumber, JUnit, or TestNG
• API Testing Tool: Rest Assured or any other (custom written solution are also legit)Task:
1. Authorize user through public REST endpoint “/rest/public/login”
2. Fetch user balance through auhtorized endpoint “/rest/public/profile/account-entries”
3.  Assert that user’s “openingBalance” == 0.00Task additional info:
    1. Test user credentials are:
      username: testuser@qa.com
      password: Parole123
    2. In case if test user has broken state, register new random one through web page.
    3. If you have experience in web test automation, additionaly you can automate this same test with the web tests as well (Not mandatory, completely up to you).
