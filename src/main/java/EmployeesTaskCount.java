import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class EmployeesTaskCount {
    private static int activeEmployeesInTasks(List<HashMap<String, String>> taskList){
        Iterator<HashMap<String, String>> iter = taskList.iterator();
        int i = 0;
        AtomicInteger count = new AtomicInteger();
        while(iter.hasNext()) {
//            Set<HashMap.Entry<String, String>> taskEntrySet = taskList.get(i).entrySet();
            HashMap<String, String> map = taskList.get(i);
            HashMap<String, String> lastMap = taskList.get(taskList.size() - 1);
            if (i + 1 == taskList.size()) break;
            HashMap<String, String> nextMap = taskList.get(i + 1);
            if(taskList.size()%2==1){
            Map<String, Boolean> lastResult = areEqualKeyValues(map, lastMap);
//            if (i == 0) {
//                lastResult = areEqualKeyValues(map, lastMap);
//            }
            Map<String, Boolean> result = areEqualKeyValues(map, nextMap);
            System.out.println(result + "\n" + lastResult);
                if(result.get("task_state") && !result.get("assignee_id")){
//                    if(i == 0) {
//                        count.getAndIncrement();
//                    }
                    count.getAndIncrement();
                }
            // ^ - исключающее или
            if ((result.get("task_state") ^ lastResult.get("task_state")) && !result.get("assignee_id")) {
                count.getAndIncrement();
            }
            if (!lastResult.get("assignee_id") && !lastResult.get("task_state")) {
                count.getAndIncrement();
            }
            if((!result.get("task_state")||result.get("task_state"))&&result.get("assignee_id")){
                count.getAndIncrement();
            }

        } else {
                Map<String, Boolean> result = areEqualKeyValues(map, nextMap);
                if(result.get("task_state") && !result.get("assignee_id")){
                    count.getAndIncrement();
                }
            }
            i++;
        }
        return count.get();
    }
    private static Map<String,Boolean> areEqualKeyValues(HashMap<String, String> first, HashMap<String, String> second){
        for (Map.Entry<String, String> e : first.entrySet()) {
            if(e.getKey() == null){
                Map<String, Boolean> m = new HashMap<>();
                m.put("assignee_id",Boolean.FALSE);
                return m;
            }
        } for (Map.Entry<String, String> e : second.entrySet()) {
            if(e.getKey() == null){
                Map<String, Boolean> m = new HashMap<>();
                m.put("assignee_id",Boolean.FALSE);
                return m;
            }
        }
            return first.entrySet().stream()
                    .collect(Collectors.toMap(e -> e.getKey(),
                            e -> e.getValue().equals(second.get(e.getKey())) && !(e.getValue() == null)));
    }
    public static void main(String[] args) {
        List<HashMap<String, String>> testTaskList = new ArrayList<>();

        LinkedHashMap<String, String> taskIdMap = new LinkedHashMap<>();
        taskIdMap.put("task_id", "1");
        taskIdMap.put("assignee_id", "001");
        taskIdMap.put("task_state", "active");
        testTaskList.add(taskIdMap);

        HashMap<String, String> taskIdMap1 = new LinkedHashMap<>();
        taskIdMap1.put("task_id", "2");
        taskIdMap1.put("assignee_id", "002");
        taskIdMap1.put("task_state", "active");
        testTaskList.add(taskIdMap1);

        HashMap<String, String> taskIdMap2 = new LinkedHashMap<>();
        taskIdMap2.put("task_id", "3");
        taskIdMap2.put("assignee_id", "001");
        taskIdMap2.put("task_state", "active");
        testTaskList.add(taskIdMap2);

        HashMap<String, String> taskIdMap3 = new LinkedHashMap<>();
        taskIdMap3.put("task_id", "4");
        taskIdMap3.put("assignee_id", "007");
        taskIdMap3.put("task_state", "disabled");
        testTaskList.add(taskIdMap3);

        System.out.println(testTaskList);

        System.out.println(activeEmployeesInTasks(testTaskList));
    }
}
