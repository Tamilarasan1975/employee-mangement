# Employee Management (Spring MVC + Spring AOP)

Java 21, Spring MVC 6, JSP/JSTL, Bean Validation, Spring AOP audit logging, JUnit 5.
Needs a Jakarta-based server: **Tomcat 10.1+**.

## Layers
| Layer | Package | Responsibility |
|-------|---------|----------------|
| Model | `model` | `Employee` + validation annotations |
| Controller | `controller` | web requests -> JSP views |
| Service | `service` | business rules (duplicate email check) |
| Repository | `repository` | in-memory storage |
| AOP | `aop` | `AuditLoggingAspect` logs every service call |
| Config | `config` | Java config (no web.xml / no XML) |

## Run
Eclipse: Import > Existing Maven Projects, then Run As > Run on Server (Tomcat 10.1).
Open http://localhost:8080/employee-management/

Tests: `mvn test` (or Run As > JUnit Test in Eclipse).
