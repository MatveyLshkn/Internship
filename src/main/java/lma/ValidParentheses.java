package lma;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//https://leetcode.com/problems/valid-parentheses/
public class ValidParentheses {
    public boolean isValid(String s) {
        Map<Character, Character> brackets = new HashMap<>();
        brackets.put('(', ')');
        brackets.put('[', ']');
        brackets.put('{', '}');

        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (brackets.containsKey(ch)) {
                stack.push(ch);
            } else if (stack.isEmpty() || brackets.get(stack.pop()) != ch) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
