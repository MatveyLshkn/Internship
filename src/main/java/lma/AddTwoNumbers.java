package lma;


//https://leetcode.com/problems/add-two-numbers/
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;

        if (l1 == null) {
            l1 = new ListNode(0);
        }
        if (l2 == null) {
            l2 = new ListNode(0);
        }

        int sum = l1.val + l2.val;

        if (sum >= 10) {
            if (l1.next == null) {
                l1.next = new ListNode(0);
            }
            l1.next.val = l1.next.val + sum / 10;
        }

        ListNode ans = new ListNode(0);
        ans.val = sum % 10;
        ans.next = addTwoNumbers(l1.next, l2.next);
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
