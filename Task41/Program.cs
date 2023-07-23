/*
Задача 41
Пользователь вводит с клавиатуры M чисел.
Посчитайте, сколько чисел больше 0 ввёл пользователь.
0, 7, 8, -2, -2 -> 2
1, -7, 567, 89, 223-> 3
*/

//подсчет числа с консоли
int Prompt(string message)
{
System.Console. Write(message); //вывод сообщения
string value = Console.ReadLine(); //считывание с консоли строки
int result = Convert.ToInt32(value); // преобразование строки в целое
return result; //возврат результата
}