package com.daelly.sample.configuration;

import com.daelly.sample.annotation.ColorImportSelector;
import com.daelly.sample.annotation.EnablePerson;
import com.daelly.sample.annotation.RegisterColor;
import com.daelly.sample.bean.*;
import org.springframework.context.annotation.Import;

/**
 *
 */
//@Import(Book.class)
//@Import(BookImportSelector.class)
//@RegisterColor(colorUsed = {Black.class, Purple.class})
//@Import(BookRegistrar.class)
@EnablePerson(name = "Daelly", age = 18, gender = true)
public class AppConfig {
}
