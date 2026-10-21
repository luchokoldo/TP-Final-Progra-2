package Models;

import Enums.MenuConst;
import Exceptions.GetItemsInPageException;
import Exceptions.InvalidOptionMenuException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Menu
{
    private String title;
    private ArrayList<String> listado;
    private Integer paginaActual;
    private Integer lastIndexSelected;
    
    public Menu()
    {
        this.title = "";
        this.listado = new ArrayList<>();
        this.paginaActual = 0;
        this.lastIndexSelected = MenuConst.RET_EXIT.getValue();
    }
    
    public Menu(String title, ArrayList<String> listado)
    {
        this.title = title;
        this.listado = listado;
        this.paginaActual = 0;
        this.lastIndexSelected = MenuConst.RET_EXIT.getValue();
    }
    
    public Boolean add(String item)
    {
        return this.listado.add(item);
    }
    
    public Boolean remove(String item)
    {
        return this.listado.remove(item);
    }
    
    public List<String> getItemsInPage() throws GetItemsInPageException
    {
        Integer from = this.getItemsInPageFrom();
        
        if (from >= this.listado.size())
        {
            return Collections.emptyList();
        }
        
        Integer to = this.getItemsInPageTo(from);
        
        return this.getList(from, to);
    }
    
    private Integer getItemsInPageFrom()
    {
        return this.paginaActual * MenuConst.PAGE_SIZE.getValue();
    }
    
    private Integer getItemsInPageTo(Integer from)
    {
        return Math.min(from + MenuConst.PAGE_SIZE.getValue(), this.listado.size());
    }
    
    private List<String> getList(Integer from, Integer to) throws GetItemsInPageException
    {
        List<String> listadoCopia;
        
        try
        {
            listadoCopia = List.copyOf(this.listado.subList(from, to));
        }
        catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException e)
        {
            throw new GetItemsInPageException(e.getMessage());
        }
        
        return listadoCopia;
    }
    
    public Integer getItemsCountInPage()
    {
        Integer from = this.getItemsInPageFrom();
        Integer to = this.getItemsInPageTo(from);
        
        return to - from;
    }
    
    public String getItem(Integer index) throws IndexOutOfBoundsException
    {
        if(index < 0 || index >= this.listado.size())
        {
            throw new IndexOutOfBoundsException();
        }
        
        return this.listado.get(index);
    }
    
    public Integer getIndexFromPage(Integer selected) throws IndexOutOfBoundsException
    {
        if (selected < 0 || selected > getItemsCountInPage())
        {
            throw new IndexOutOfBoundsException();
        }
        
        return selected + this.getItemsInPageFrom();
    }
    
    public Boolean hasItems()
    {
        return this.listado.isEmpty();
    }
    
    public String getTitle()
    {
        return this.title;
    }
    
    public void setTitle(String title) throws NullPointerException
    {
        if(title == null)
        {
            throw new NullPointerException();
        }
        
        this.title = title;
    }
    
    public Integer getLastIndexSelected()
    {
        return this.lastIndexSelected;
    }
    
    public Integer getCurrentPage()
    {
        return this.paginaActual + 1;
    }
    
    public Boolean hasPreviousPage()
    {
        return this.paginaActual > 0;
    }
    
    public Boolean hasNextPage()
    {
        return this.getCurrentPage() * MenuConst.PAGE_SIZE.getValue() < this.listado.size();
    }
    
    public Boolean moveNextPage()
    {
        if(hasNextPage() == false)
        {
            return false;
        }
        
        this.paginaActual++;
        
        return true;
    }
    
    public Boolean movePreviousPage()
    {
        if (hasPreviousPage() == false)
        {
            return false;
        }
        
        this.paginaActual--;
        
        return true;
    }
    
    public Boolean checkValidOption(Integer selected) throws InvalidOptionMenuException
    {
        if (selected < MenuConst.EXIT.getValue() || selected > MenuConst.NEXT.getValue() ||
                selected == MenuConst.PREV.getValue() && this.hasPreviousPage() == false ||
                selected == MenuConst.NEXT.getValue() && this.hasNextPage() == false ||
                selected > this.getItemsCountInPage() && selected < MenuConst.PREV.getValue())
        {
            throw new InvalidOptionMenuException("Opcion incorrecta");
        }
        
        return true;
    }
    
    public void resolve(Integer selected)
    {
        if(selected == MenuConst.EXIT.getValue())
        {
            this.lastIndexSelected = MenuConst.RET_EXIT.getValue();
        }
        else if (selected == MenuConst.PREV.getValue())
        {
            this.movePreviousPage();
            
            this.lastIndexSelected = MenuConst.RET_PREV.getValue();
        }
        else if (selected == MenuConst.NEXT.getValue())
        {
            this.moveNextPage();
            
            this.lastIndexSelected = MenuConst.RET_NEXT.getValue();
        }
        else
        {
            this.lastIndexSelected =  (selected - 1) + this.getItemsInPageFrom();
        }
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Menu menu))
        {
            return false;
        }
        return Objects.equals(this.getTitle(), menu.getTitle()) && Objects.equals(this.listado, menu.listado);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.getTitle(), this.listado);
    }
    
    @Override
    public String toString()
    {
        return "Menu{" +
                "title='" + title + '\'' +
                ", listado=" + listado +
                ", paginaActual=" + paginaActual +
                ", lastIndexSelected=" + lastIndexSelected +
                '}';
    }
}
