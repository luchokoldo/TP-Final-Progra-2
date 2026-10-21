package Views;

import Enums.MenuConst;
import Exceptions.InvalidOptionMenuException;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Scanner;

public class MenuView
{
    private final Scanner scanner;
    
    public MenuView(Scanner scanner)
    {
        this.scanner = scanner;
    }
    
    public Integer showMenu(String title, List<String> list, Integer page, Boolean hasPrevious, Boolean hasNext) throws InvalidOptionMenuException
    {
        String menu = this.buildMenu(title, list, page, hasPrevious, hasNext);
        
        Integer option = Integer.MIN_VALUE;
        
        do
        {
            this.clearScreen();
            
            System.out.print(menu);
            option = getValidOption();
            
        }
        while (option == Integer.MIN_VALUE);
        
        return option;
    }
    
    private String buildMenu(String title, List<String> list, Integer page, Boolean hasPrevious, Boolean hasNext)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n\t" + title + "(Pagina " + page + ")\n\n");
        
        if(list.isEmpty() == true)
        {
            sb.append("Menu vacio.\n\n");
        }
        else
        {
            int count = 0;
            
            for(String item : list)
            {
                sb.append(++count + "." + item + "\n");
            }
            
            sb.append("\n");
            
            if(hasPrevious == true)
            {
                sb.append(MenuConst.PREV.getValue() + ". Volver\n");
            }
            
            if(hasNext == true)
            {
                sb.append(MenuConst.NEXT.getValue() + ". Siguiente\n");
            }
        }
        
        sb.append(MenuConst.EXIT.getValue() + ". Salir\n\n");
        sb.append("Elegir opcion: ");
        
        return sb.toString();
    }
    
    private void clearScreen()
    {
        System.out.print("\033c");
        System.out.flush();
    }
    
    private int getValidOption() throws InvalidOptionMenuException
    {
        int option = 0;
        
        try
        {
            option = this.scanner.nextInt();
            
            this.scanner.nextLine();
        }
        catch (InputMismatchException e)
        {
            this.scanner.nextLine();
            
            throw new InvalidOptionMenuException("Opcion incorrecta, ingresa un numero");
        }
        catch (NoSuchElementException | IllegalStateException e)
        {
            this.scanner.nextLine();
            
            throw new InvalidOptionMenuException(e.getMessage());
        }
        
        return option;
    }
    
    public void showOpcionError()
    {
        System.out.println("[Menu] Opcion incorrecta");
    }
    
    public void showOpcionError(String message)
    {
        System.out.println("[Menu] " + message);
    }
}
