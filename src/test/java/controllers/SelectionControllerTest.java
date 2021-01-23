package controllers;

import org.hamcrest.core.AllOf;
import org.junit.*;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

public class SelectionControllerTest {
//    SelectionController controller = new SelectionControllerImpl(menuAdapter, popupMenuAdapter, mouseAdapter, boundsController);

    @Before // Вызывается перед каждым тестовым классом
    public void setUp() {

    }

    @BeforeClass // Вызывается оди раз перед выполнением тестов для класса
    public static void setUpBeforeClass() {

    }

    @After // Вызывается после каждого тестового метода
    public void tearDown() {

    }


    @Test
    @Ignore
    public void resizeSelection() {
        fail("Message"); // Указывает, чтобы тест завалился
        assertTrue(10 > 0); //Проверяет логическое условие
        assertNotNull("Message", new Object()); //Проверяет на null.
        assertNull("Message", null);
        assertEquals("Message", 10.0, 10.2, 0.2);
        assertSame(new Object(), new Object());
        assertThat(new Object(), AllOf.allOf()); //Использует матчеры для читаемости
        assumeFalse(false); //Assume не генерирует ошибок, а выдает сообщение
    }

    @Test
    public void move() {

    }

    @Test
    public void deselect() {
    }

    @Test
    public void cut() {
    }

    @Test
    public void copy() {
    }

    @Test
    public void paste() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void flipHorizontally() {
    }

    @Test
    public void flipVertically() {
    }

    @Test
    public void rotate() {
    }

    @Test
    @Ignore("This method tested before")
    public void resize() {
    }
}