
BackEnd Test Framework - SnowBall QA

=========================

Version 1.0
-For every api, extends the base class XqApi
-For json schema file, use api instance to define: setJsonSchemaFilePath()
-For json analyze， use org.json directly, or use the function embedded in the api instance.

Review Result of Version 1.0
1, Https is not correctly configured, currently is using default http, client is not verified.(To be verified)
2, API definition should use direct name, including URI path, API definition should support quantity production. (Done)
3, TestAccount instance, logged in for every request fire, should be removed. (Done)
4, Response analyze, should support getting via absolute path. (Done)
5, (Low priority) Log.
6, (Low priority) Switch test environment.
7, (Low priority) Deploy to xq gitlab.
8, change schema lib everit -> fge
9, implement test rail, based on 5
10, support request fire without parameterList
