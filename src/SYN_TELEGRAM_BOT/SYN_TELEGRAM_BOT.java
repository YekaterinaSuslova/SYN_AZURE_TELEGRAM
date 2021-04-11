package SYN_TELEGRAM_BOT;


import java.io.File;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

class MyTelegramBot extends TelegramLongPollingBot {

    // Метод получения команд бота, тут ничего не трогаем
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(doCommand(update.getMessage().getChatId(),
                    update.getMessage().getText()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
            }
        }
    }

    // Тут задается нужное значение имени бота
    @Override
    public String getBotUsername() {
        return "SYN_Math_bot";
    }

    // Тут задается нужное значение токена
    @Override
    public String getBotToken() {
        return "1619444009:AAFfv0v9vMuvR-cCdBNOXgEpa6MxhM82CAg";
    }

    // Метод обработки команд бота
    public String doCommand(long chatId, String command) {
        try {
                sendPhoto(new SendPhoto().setChatId(chatId).setNewPhoto(new File("formula.png")));
            } catch (TelegramApiException e) {
            }
        
        if (command.startsWith("/solve")) {
            
            String[] param = command.split(" ");
            if (param.length > 1) {
                return "y= " + getSolve(Double.parseDouble(param[1]), Double.parseDouble(param[2]), Double.parseDouble(param[3]));
            } else {
                return "Для решения примера используйте команду /solve и введите переменные x, a, b через пробел";
            }
        }
        return "Для решения примера используйте команду /solve и введите переменные x, a, b через пробел";
    }

     private String getSolve(double x, double a, double b) {
        StringBuilder answer = new StringBuilder();
        double y;

        if (x > 5) {
            y = (5 * a * b) / ((x * x) + (a * a));
        } else {
            y = (4 * (a + b - x) * (a + b - x));

        }

        answer.append(y);
        return String.format("%.3f", y);
    }
}
public class SYN_TELEGRAM_BOT {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            // ЗАПУСКАЕМ КЛАСС НАШЕГО БОТА
            botsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
        }
    }
}
