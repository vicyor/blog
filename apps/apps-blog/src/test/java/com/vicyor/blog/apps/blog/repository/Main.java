package com.vicyor.blog.apps.blog.repository;

import java.util.*;
import java.util.stream.*;

//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner=new Scanner(System.in);
//        List<Integer> pids = Stream.of(scanner.nextLine().split(" ")).map(
//                str -> Integer.valueOf(str)
//        ).collect(Collectors.toList());
//        List<Integer> ppids = Stream.of(scanner.nextLine().split(" ")).map(
//                str -> Integer.valueOf(str)
//        ).collect(Collectors.toList());
//        int pid = Integer.parseInt(scanner.next());
//        if(pid==0){
//            Set <Integer>set=new HashSet<>();
//            for(Integer num:pids){
//                set.add(num);
//            }
//            for(Integer num:ppids){
//                set.add(num);
//            }
//            set.remove(0);
//            System.out.println(set.size()+1);
//            return;
//        }
//        Map<Integer,List<Integer>> map=new HashMap<>();
//        for(int i=0;i<ppids.size();i++){
//            Integer ppid = ppids.get(i);
//            List<Integer> list = map.get(ppid);
//            if(list==null){
//                list=new ArrayList<>();
//            }
//            list.add(pids.get(i));
//            map.put(ppid,list);
//        }
//        int result = digui(map, pid);
//        //加上自身
//        System.out.println(result+1);
//    }
//    public static int digui(Map<Integer,List<Integer>> map,int index){
//        List<Integer> list = map.get(index);
//        if(list==null)
//            return 0;
//        int result=list.size();
//        for(int i=0;i<list.size();i++){
//            result+=digui(map,list.get(i));
//        }
//        return result;
//    }
//}
public class Main {
    public static void main(String[] args) {
        //0 1 2 3 4 5 6 7 8 9
        //0 1 3
        //0 1 4
        //0 2 5
        //0 2 6
        try {
            ArrayList<Integer> list = new ArrayList<>();
            Scanner scanner = new Scanner(System.in);
            int level = Integer.valueOf(scanner.nextLine());
            //获取树节点
            List<Integer> nodes = Stream.of(scanner.nextLine().split(" ")).map(
                    str -> Integer.valueOf(str)
            ).collect(Collectors.toList());
            String str = scanner.nextLine();
            String[] strs = str.split(" ");
            int a = Integer.valueOf(strs[0]);
            int b = Integer.valueOf(strs[1]);
            int aIndex = nodes.indexOf(a);
            int bIndex = nodes.indexOf(b);
            if(aIndex==-1||bIndex==-1){
                System.out.println(-1);
                return;
            }
            //a small , b big
            aIndex = aIndex < bIndex ? aIndex : bIndex;
            bIndex = aIndex < bIndex ? bIndex : aIndex;
            if (aIndex == bIndex) {
                System.out.println(a);
            } else {
                while (aIndex < bIndex) {
                    bIndex = (bIndex - 1) / 2;
                    aIndex = aIndex < bIndex ? aIndex : bIndex;
                    bIndex = aIndex < bIndex ? bIndex : aIndex;
                }
                System.out.println(nodes.get(aIndex));
            }
        } catch (Exception e) {
            System.out.println(-1);
        }
    }
}