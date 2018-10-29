package seedu.address.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.logic.commands.GradebookAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertNotNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.GradebookAddCommandParser.*;

public class GradebookAddCommandParserTest {
    private GradebookAddCommandParser parser = new GradebookAddCommandParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parse_invalidFormat_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradebookAddCommand.MESSAGE_USAGE);

        //invalid arguments found
        String arg = "this is an invalid format";

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(arg);
    }

    @Test
    public void parse_validArgsWithOptionalParams_success() throws ParseException {
        //valid arguments without optional arguments
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int maxMarks = 20;
        int weightage = 10;

        String argsWithoutOptionalParam = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName;
        GradebookAddCommand gradebookAddCommand = parser.parse(argsWithoutOptionalParam);
        assertNotNull(gradebookAddCommand);

        //valid arguments including optional argument, Max Marks
        String argsWithMaxMarks = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + maxMarks;
        gradebookAddCommand = parser.parse(argsWithMaxMarks);
        assertNotNull(gradebookAddCommand);

        //valid arguments including optional argument, Weightage
        String argsWithWeightage = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_WEIGHTAGE
                + weightage;
        gradebookAddCommand = parser.parse(argsWithWeightage);
        assertNotNull(gradebookAddCommand);

        //valid arguments including optional arguments, Max Marks and Weightage
        String argsWithAllOptionalParams = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + maxMarks
                + " "
                + PREFIX_GRADEBOOK_WEIGHTAGE
                + weightage;
        gradebookAddCommand = parser.parse(argsWithAllOptionalParams);
        assertNotNull(gradebookAddCommand);
    }

    @Test
    public void parse_invalidMaxMarksType_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_ERROR);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String invalidMaxMarks = "20ps";

        //invalid maximum marks type
        String argsWithInvalidMaxMarks = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + invalidMaxMarks;
        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argsWithInvalidMaxMarks);
    }

    @Test
    public void parse_invalidWeightageType_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_WEIGHTAGE_ERROR);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        String invalidWeightage = "5ps";

        //invalid weightage type
        String argsWithInvalidWeightage = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_WEIGHTAGE
                + invalidWeightage;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argsWithInvalidWeightage);
    }

    @Test
    public void parse_missingMandatoryParams_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_EMPTY_INPUTS);
        //missing mandatory fields : module code and grade component name
        String argsWithoutMandatoryParams = " "
                + PREFIX_MODULE_CODE
                + " "
                + PREFIX_GRADEBOOK_ITEM;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argsWithoutMandatoryParams);
    }

    @Test
    public void parse_invalidMaxMarksRange_throwsParseException() throws ParseException {
        String expectedMessage = String.format(MESSAGE_MAX_MARKS_INVALID);
        String moduleCode = "CS2113";
        String gradebookComponentName = "Finals";
        int invalidMaxMarksRange = -10;

        //invalid weightage type
        String argsWithInvalidMaxMarksRange = " "
                + PREFIX_MODULE_CODE
                + moduleCode
                + " "
                + PREFIX_GRADEBOOK_ITEM
                + gradebookComponentName
                + " "
                + PREFIX_GRADEBOOK_MAXMARKS
                + invalidMaxMarksRange;

        thrown.expect(ParseException.class);
        thrown.expectMessage(expectedMessage);
        parser.parse(argsWithInvalidMaxMarksRange);
    }
}
