package Controllers;

import Enums.MenuConst;
import Exceptions.GetItemsInPageException;
import Exceptions.InvalidOptionMenuException;
import Exceptions.MenuDoNotExistsException;
import Models.Menu;
import Views.MenuView;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MenuController
{
    HashMap<String, Menu> menuMap;
    
    public MenuController()
    {
        menuMap = new HashMap<>();
    }
    
    public Boolean addMenu(String key, Menu menu)
    {
        if(menuMap.containsKey(key) == true)
        {
            return false;
        }
        
        menuMap.put(key, menu);
        
        return true;
    }
    
    public Menu updateMenu(String key, Menu menu)
    {
        return menuMap.put(key, menu);
    }
    
    public Menu getMenu(String key)
    {
        return menuMap.get(key);
    }
    
    public Boolean removeMenu(String key)
    {
        if (menuMap.containsKey(key) == false)
        {
            return false;
        }
        
        menuMap.remove(key);
        
        return true;
    }
    
    public Integer showMenu(String key) throws MenuDoNotExistsException, GetItemsInPageException
    {
        Menu menu = this.getMenu(key);
        
        if (menu == null)
        {
            throw new MenuDoNotExistsException("No such menu exists!");
        }
        
        Scanner sc = new Scanner(System.in);
        MenuView menuView = new MenuView(sc);
        Integer opcion = MenuConst.INVALID_OPTION.getValue();
        
        do
        {
            String title = menu.getTitle();
            List<String> list = menu.getItemsInPage();
            Integer page = menu.getCurrentPage();
            
            try
            {
                opcion = menuView.showMenu(title, list, page, menu.hasPreviousPage(), menu.hasNextPage());
                
                menu.checkValidOption(opcion);
                
                menu.resolve(opcion);
            }
            catch (InvalidOptionMenuException e)
            {
                menuView.showOpcionError(e.getMessage());
                
                opcion = MenuConst.INVALID_OPTION.getValue();
            }
        }
        while (opcion == MenuConst.PREV.getValue() ||
                opcion == MenuConst.NEXT.getValue() ||
                opcion == MenuConst.INVALID_OPTION.getValue()
        );
        
        return menu.getLastIndexSelected();
    }
}
