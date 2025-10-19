## Модуль 4. Spring

---

### Задача:
Добавить в user-service поддержку Spring и разработать API, которое позволит управлять данными.

### Требования:
* Использовать необходмиые модули spring(boot, web, data etc).
* Реализовать api для получения, создания, обновления и удаления юзера. Важно, entity не должен возвращаться из 
контроллера, необходимо использовать dto.
* Заменить Hibernate на Spring data JPA.
* Написать тесты для API (можно делать это при помощи mockMVC или других средств).

---
### Описание:
* Директория с [проектом](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/tree/main/src/main/java/com/akulov/springboot/userservice_data_jpa)
* Прямые ссылки: 
  * [entity](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/entity/User.java)
  * [repository](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/repository/UserRepository.java)
  * [service](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/service/UserServiceImpl.java)
  * [dto](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/dto/UserDto.java)
  * [mappings](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/utils/MappingUtils.java)
  * [controller](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/controller/UserRESTController.java)
  * [точка входа](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/java/com/akulov/springboot/userservice_data_jpa/UserServiceDataJpaApplication.java)
  * [тесты](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/test/java/com/akulov/springboot/userservice_data_jpa/controller/UserRESTControllerTest.java)
  * [properties](https://github.com/MikhailAkulov/springboot_user-service_data_jpa/blob/main/src/main/resources/application.properties)