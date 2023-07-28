#!/bin/bash
echo "Input calculation operand"
echo"+, -, *, /, !, %"
read operand

if [ "$operand" == "!" ]; then
    echo "Enter a number for factorial:"
    read num1
else
    echo "Enter the first number:"
    read num1
    echo "Enter the second number:"
    read num2
fi

function factorial {
    local num=$1
    if (( num == 0 )); then
        echo 1
    else
        echo $(( num * $(factorial $((num - 1))) ))
    fi
}

case $operand in
    "+")
        result=$(( num1 + num2 ))
        ;;
    "-")
        result=$(( num1 - num2 ))
        ;;
    "*")
        result=$(( num1 * num2 ))
        ;;
    "/")
        result=$(( num1 / num2 ))
        ;;
    "%")
        result=$(( num1 % num2 ))
        ;;
    "!")
        result=$(factorial $num1)
        ;;
esac

echo "Result: $result"