package examples.calculator;

public class CalculableFactory implements ICalculableFactory {
    public Calculable create(int primaryArg, boolean logFlag) {
        if (logFlag) {
            Calculable calculator =  new Calculator(primaryArg);
            return new LoggableCalculator(calculator);
        }
        return new Calculator(primaryArg);
    }
}
