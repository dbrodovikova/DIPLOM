package examples.calculator;

public interface ICalculableFactory {
    Calculable create(int primaryArg, boolean logFlag);
}
