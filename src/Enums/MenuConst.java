package Enums;

public enum MenuConst
{
    PAGE_SIZE(7),
    PREV(8),
    NEXT(9),
    EXIT(0),
    RET_PREV(Integer.MIN_VALUE),
    RET_NEXT(Integer.MAX_VALUE),
    RET_EXIT(-1),
    INVALID_OPTION(-1);
    
    private final Integer value;
    
    MenuConst(int i) {
        this.value = i;
    }
    
    public final Integer getValue()
    {
        return this.value;
    }
}
