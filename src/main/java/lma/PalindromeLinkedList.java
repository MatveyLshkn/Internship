package lma;

//https://leetcode.com/problems/palindrome-linked-list/
public class PalindromeLinkedList {

    private ListNode reverse(ListNode root) {
        ListNode curr = root;
        ListNode prev = null;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public boolean isPalindrome(ListNode head) {
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode rev = reverse(slow.next);

        while (rev != null) {
            if (head.val != rev.val) {
                return false;
            }
            head = head.next;
            rev = rev.next;
        }

        return true;
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
