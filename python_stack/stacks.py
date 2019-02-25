def main(test_string: str):
    stack = list(test_string)
    stack.reverse()
    temp_stack = list()
    for letter in stack:
        if letter == 'C':
            stack.pop()
            break
        temp_stack.append(stack.pop())
    stack.reverse()
    print(f'Remaining string: {"".join(stack)}')
    print(f'Current temp_stack: {temp_stack}')
    
    for letter in ''.join(stack):
        try:
            reverse_letter = temp_stack.pop()
            if letter == reverse_letter:
                continue
            else:
                return False
        except IndexErr:
            return False

    return len(temp_stack) == 0
