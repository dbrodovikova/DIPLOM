/*
Задача 29
Напишите программу, которая задаёт массив из 8 элементов и выводит их на экран.
1, 2, 5, 7, 19, 6, 1, 33 -> [1, 2, 5, 7, 19, 6, 1, 33]
*/

int [] numbers = new int[8];
Console.Write("[");

for (int i = 0; i < numbers.Length; i++)
 {
    numbers [i] = new Random().Next(0, 11);
    Console.Write(" " + Method (i) + " ");
 }
Console.Write("]");

int Method (int a)
{
    return numbers[a];
}