import java.util.*;

public class EmployeesTaskCount {

    public static int activeEmployeeInTask(List<HashMap<String,String>> taskList){
        int countEmployee = 0;
        String employee = null;
        String nextEmployee = null;
        Iterator<HashMap<String, String>> iterator = taskList.iterator();
//        while(iterator.hasNext()) {
        for (int i = 0; i < taskList.size(); i++) {
            HashMap<String, String> taskMap = taskList.get(i);
            HashMap<String, String> taskMapNext = null;
            if (i + 1 != taskList.size() - 1) {
                taskMapNext = taskList.get(i + 1);
            }
            Set<Map.Entry<String, String>> taskEntrySet = taskMap.entrySet();
            Set<Map.Entry<String, String>> taskEntrySetNext = taskMapNext.entrySet();
            for (Map.Entry<String, String> taskEntry : taskEntrySet) {
                for (Map.Entry<String, String> taskEntryNext : taskEntrySetNext) {
                    if (taskEntry.getKey().equals("assignee_id")) {
                        employee = taskEntry.getValue();
                        nextEmployee = taskEntryNext.getValue();
                    }
                    if (taskEntry.getKey().equals("task_state")) {
                        String state = taskEntry.getValue();
                        if (state.equals("active") && !employee.equals(nextEmployee)) {
                            countEmployee++;
                        }
                    }
                }
            }
        }
        return countEmployee;
    }

    public static void main(String[] args) {
        List<HashMap<String,String>> testList = new ArrayList<>();
//        [, {task_id = 2, assignee_id = 002,
//                task_state = active}, {task_id = 3, assignee_id = 001, task_state = active}}, {task_id = 4,
//            assignee_id = 007, task_state = disabled}];
        HashMap<String,String> taskIdMap = new HashMap<>();
        taskIdMap.put("task_id","1");
        HashMap<String,String> assigneeIdMap = new HashMap<>();
        assigneeIdMap.put("assignee_id","001");
        HashMap<String,String> taskStateMap = new HashMap<>();
        taskStateMap.put("task_state","active");
        testList.add(taskIdMap);
        testList.add(assigneeIdMap);
        testList.add(taskStateMap);

        System.out.println(activeEmployeeInTask(testList));
    }
}
