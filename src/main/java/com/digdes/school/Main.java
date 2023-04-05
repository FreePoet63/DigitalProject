package com.digdes.school;

import java.util.*;

public class Main {

    public static void main(String... args){
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
//Вставка строки в коллекцию
            List<Map<String,Object>> result1 = starter.execute("INSERT VALUES 'lastName' = 'Федоров', 'id' = 3, 'age' = 40, 'active' = true");
            List<Map<String,Object>> result2 = starter.execute("INSERT VALUES 'lastName' = 'Попов', 'id' = 5, 'age' = 20, 'active' = true");
            List<Map<String,Object>> result7 = starter.execute("INSERT VALUES 'lastName' = 'Ларина', 'id' = 5, 'age' = 70, 'active' = false");

//Изменение значения которое выше записывали
            List<Map<String,Object>> result3 = starter.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3");

//Удаление строки из коллекции
            //List<Map<String, Object>> result4 = starter.execute("DELETE WHERE 'id'=5");

//Получение всех данных из коллекции (т.е. в данном примере вернется 1 запись)
            List<Map<String,Object>> result5 = starter.execute("SELECT");
            System.out.println(result5);


           /* List<Map<String,Object>> result5 = starter.execute("SELECT");
            List<Map<String, Object>> result1 = starter.execute("SELECT WHERE 'age' > 30");
            List<Map<String, Object>> result2 = starter.execute("SELECT WHERE 'lastName' LIKE '%в%'");
            List<Map<String, Object>> result3 = starter.execute("SELECT WHERE 'age' >= 25 AND 'lastName' ILIKE '%а%'");
            List<Map<String, Object>> result4 = starter.execute("SELECT WHERE 'age' <= 25 OR 'lastName' ILIKE*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
