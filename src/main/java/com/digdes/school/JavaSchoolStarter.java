package com.digdes.school;

import java.util.*;

public class JavaSchoolStarter {
    private List<Map<String, Object>> data = new ArrayList<>();

    public JavaSchoolStarter() {
    }

    public List<Map<String, Object>> execute(String query) {
        String[] queryList = query.split("\\s+", 2);
        String command = queryList[0];

        if (command.equalsIgnoreCase("INSERT")) {
            insert(queryList[1]);
        } else if (command.equalsIgnoreCase("UPDATE")) {
            update(queryList[1]);
        } else if (command.equalsIgnoreCase("SELECT")) {
            return select(queryList);
        } else if (command.equalsIgnoreCase("DELETE")) {
            delete(queryList[1]);
        }
        return new ArrayList<>();
    }

    private void delete(String s) {
        String[] queryParts = s.split(" ");
        if (queryParts[0].equalsIgnoreCase("WHERE")) {
            int idToDelete = Integer.parseInt(queryParts[1].split("=")[1]);
            Iterator<Map<String, Object>> iterator = data.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> item = iterator.next();
                if (item.containsKey("id") && Integer.parseInt(String.valueOf(item.get("id"))) == idToDelete) {
                    iterator.remove();
                }
            }
        }
    }

    private List<Map<String, Object>> select(String[] queryList) {
        List<Map<String,Object>> filteredData = new ArrayList<>();
        if (queryList.length > 1 && queryList[1].startsWith("WHERE")) {
            String condition = queryList[1].substring(6);
            String[] conditions = condition.split(" AND | OR ");

            for (Map<String, Object> item : data) {
                boolean isMatch = true;
                for (String conditionItem : conditions) {
                    String[] conditionArray = conditionItem.trim().split(" ");
                    String fieldName = conditionArray[0].replace("'", "").trim();
                    String operator = conditionArray[1].trim();
                    String value = conditionArray[2].replace("'", "").trim();

                    if (item.containsKey(fieldName)) {
                        Object fieldValue = item.get(fieldName);
                        if (operator.equals("=") && !fieldValue.equals(value)) {
                            isMatch = false;
                            break;
                        } else if (operator.equals("LIKE") && !((String)fieldValue).contains(value)) {
                            isMatch = false;
                            break;
                        } else if (operator.equals(">") && Integer.parseInt((String)fieldValue) <= Integer.parseInt(value)) {
                            isMatch = false;
                            break;
                        } else if (operator.equals("<") && Integer.parseInt((String)fieldValue) >= Integer.parseInt(value)) {
                            isMatch = false;
                            break;
                        } else if (operator.equals(">=") && Integer.parseInt((String)fieldValue) < Integer.parseInt(value)) {
                            isMatch = false;
                            break;
                        } else if (operator.equals("<=") && Integer.parseInt((String)fieldValue) > Integer.parseInt(value)) {
                            isMatch = false;
                            break;
                        } else if (operator.equals("ILIKE") && !((String)fieldValue).toUpperCase().contains(value.toUpperCase())) {
                            isMatch = false;
                            break;
                        }
                    } else {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    filteredData.add(item);
                }
            }
        } else {
            filteredData.addAll(data);
        }

        return filteredData;
    }

    private void update(String s) {
        String valueString = s.replace("VALUES ", "");
        valueString = valueString.replace("'", "");
        String[] values = valueString.split(" where ");

        Map<String, Object> updateData = new HashMap<>();
        String[] updates = values[0].split(", ");
        for (String update : updates) {
            String[] pair = update.split("=");
            updateData.put(pair[0].trim(), pair[1].trim());
        }

        String[] conditions = values[1].split("=");
        String key = conditions[0].trim();
        Object value = conditions[1].trim();

        for (Map<String, Object> data : data) {
            if (data.get(key).equals(value)) {
                for (Map.Entry<String, Object> entry : updateData.entrySet()) {
                    data.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private void insert(String s) {
        String valueString = s.replace("VALUES ", "");
        valueString = valueString.replace("'", "");
        String[] values = valueString.split(", ");

        Map<String, Object> newData = new HashMap<>();
        for (String value : values) {
            String[] pair = value.split("=");
            newData.put(pair[0].trim(), pair[1].trim());
        }
        data.add(newData);
    }
}








