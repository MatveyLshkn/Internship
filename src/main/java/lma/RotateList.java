package lma;

//https://leetcode.com/problems/rotate-list/
public class RotateList {

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode curr = head;
        int length = 1;

        while (curr.next != null) {
            curr = curr.next;
            length++;
        }

        curr.next = head;
        ListNode prev = null;

        int timesToSpin = length - (k % length) + 1;
        for (int i = 0; i < timesToSpin ; i++) {
            prev = curr;
            curr = curr.next;
        }

        prev.next = null;

        return curr;
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
