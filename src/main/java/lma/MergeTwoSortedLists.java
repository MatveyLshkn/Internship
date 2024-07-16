package lma;

import java.util.*;

//https://leetcode.com/problems/merge-two-sorted-lists/description/
public class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        ListNode ans = null;
        if (list1.val < list2.val) {
            ans = new ListNode(list1.val);
            ans.next = mergeTwoLists(list1.next, list2);
        } else {
            ans = new ListNode(list2.val);
            ans.next = mergeTwoLists(list1, list2.next);
        }
        return ans;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}


