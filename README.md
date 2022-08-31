## Тестирование

### Моки
* Создание, обучение, проверка без Spring просто mock - [NothingDoMock()](src/test/java/com/example/learntest/service/PersonServiceTest_NothingDoMock.java)
* Создание, обучение, проверка для Spring bean mock  - [BeanMock()](src/test/java/com/example/learntest/service/PersonServiceTest_SpringMock.java)

### Unit-тесты
https://www.youtube.com/watch?v=QrSR1fm9JwA  
Проверяем что корректно работает класс при корректной работе всего остального.  
Мокаем всё необходиемое окружение, тестируем только публичные методы.  
Проверяем поведение через Assert + spy методы моков.  
[UnitWithoutSpring()](src/test/java/com/example/learntest/service/PersonServiceTest_Unit.java)
 
### Интеграционные тесты
Тестирование работы целиком приложения или нескольких связанных классов.  
Стараемся не использовать мок-объекты а давать тестовое окружение, максимально близкое к реальному (@TestPropertySource).  
* web  (https://www.youtube.com/watch?v=Lnc3o8cCwZY https://spring.io/guides/gs/testing-web/)  
Гасить SS @AutoConfigureMockMvc(addFilters=false) - отключит все фильтры, но сам SS работает.  
Проверять на странице у конкретного поля по xPath:  
```.andExpect(xpath("путь к объекту в xpath").string("значение поля")) ```    
```.andExpect(xpath("путь к объекту в xpath").nodeCount("сколько таких элементов должно быть")) ```  
[WebIntegrationTest()](src/test/java/com/example/learntest/WebIntegrationTest.java)


* rest  
1.Через mockMVC.perform(...).andExpect(jsonPatch) (https://www.youtube.com/watch?v=N_e8qcYzHIA)    
2.Через TestRestTemplate с обыными запросами, маппингом в объекты и проверкой полученного результата (https://www.baeldung.com/spring-boot-testresttemplate).  
[RestIntegrationTest()](src/test/java/com/example/learntest/RestIntegrationTest.java)  


* security  
Для передачи креденшелов добавляем в pom spring-security-test  
[SecurityIntegrationTest()](src/test/java/com/example/learntest/SecurityIntegrationTest.java)



