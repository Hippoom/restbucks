package com.restbucks.commandhandling.gateway;

import com.restbucks.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Hashtable;
import java.util.Map;

@Component
public class CommandGateway {
    private Map<Class<?>, CommandHandlerAdatper> handlers = new Hashtable<>();

    public <T> T send(Object command) {
        CommandHandlerAdatper handler = handlers.get(command.getClass());
        try {
            return handler.handle(command);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CannotDispatchCommandException(command, e);
        }
    }



    public void register(Object commandHandler) {
        Method[] declaredMethods = commandHandler.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            CommandHandler handlerAnnotation = method.getDeclaredAnnotation(CommandHandler.class);
            if (handlerAnnotation != null) {
                Parameter[] parameters = method.getParameters();
                if (parameters.length == 1) {
                    Parameter parameter = parameters[0];
                    Class<?> commandType = parameter.getType();
                    method.setAccessible(true);
                    this.handlers.put(commandType, new CommandHandlerAdatper(commandHandler, method));
                }
            }
        }
    }

    public class CommandHandlerAdatper {
        private final Object commandHandler;
        private final Method method;

        public CommandHandlerAdatper(Object commandHandler, Method method) {
            this.commandHandler = commandHandler;
            this.method = method;
        }

        public <T> T handle(Object command) throws InvocationTargetException, IllegalAccessException {
            return (T) this.method.invoke(commandHandler, command);
        }
    }
}
