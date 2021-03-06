package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_LOCKED_SYSTEM;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClassAddCommand;
import seedu.address.logic.commands.ClassAddStudentAttendanceCommand;
import seedu.address.logic.commands.ClassAddStudentCommand;
import seedu.address.logic.commands.ClassDeleteCommand;
import seedu.address.logic.commands.ClassDeleteStudentAttendanceCommand;
import seedu.address.logic.commands.ClassDeleteStudentCommand;
import seedu.address.logic.commands.ClassEditCommand;
import seedu.address.logic.commands.ClassListCommand;
import seedu.address.logic.commands.ClassListStudentAttendanceCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CourseAddCommand;
import seedu.address.logic.commands.CourseDeleteCommand;
import seedu.address.logic.commands.CourseEditCommand;
import seedu.address.logic.commands.CourseListCommand;
import seedu.address.logic.commands.CourseListStudentsCommand;
import seedu.address.logic.commands.DebugCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GradeAddCommand;
import seedu.address.logic.commands.GradeGraphCommand;
import seedu.address.logic.commands.GradeListCommand;
import seedu.address.logic.commands.GradebookAddCommand;
import seedu.address.logic.commands.GradebookDeleteCommand;
import seedu.address.logic.commands.GradebookEditCommand;
import seedu.address.logic.commands.GradebookFindCommand;
import seedu.address.logic.commands.GradebookListCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.commands.ModuleAddCommand;
import seedu.address.logic.commands.ModuleDeleteCommand;
import seedu.address.logic.commands.ModuleEditCommand;
import seedu.address.logic.commands.ModuleEnrolCommand;
import seedu.address.logic.commands.ModuleFindCommand;
import seedu.address.logic.commands.ModuleListCommand;
import seedu.address.logic.commands.ModuleViewCommand;
import seedu.address.logic.commands.NoteAddCommand;
import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.logic.commands.NoteEditCommand;
import seedu.address.logic.commands.NoteExportCommand;
import seedu.address.logic.commands.NoteFindCommand;
import seedu.address.logic.commands.NoteListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StudentAddCommand;
import seedu.address.logic.commands.StudentEditCommand;
import seedu.address.logic.commands.StudentFindCommand;
import seedu.address.logic.commands.StudentListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.UserManager;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used to separate multiple command words and args
     */
    private static final Pattern ADVANCED_COMMAND_FORMAT =
            Pattern.compile("(?<commandWords>.*?\\S+((?<=find)|(?=(?:\\s+-?[0-9]|\\s+[a-z]+\\/))|$))(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = ADVANCED_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWords").trim().replaceAll(" +", " ");
        final String arguments = matcher.group("arguments");

        // for expedited debugging
        if (commandWord.equals("serenity")) {
            UserManager.getInstance().setAuthenticated(true);
            throw new ParseException("Authentication system disarmed.");
        }
        // halts command execution if user is not currently logged in.
        if (!commandWord.equals(LoginCommand.COMMAND_WORD) && !UserManager.getInstance().isAuthenticated()
                && !UserManager.getInstance().isDisarmAuthSystem()) {
            throw new ParseException(MESSAGE_LOCKED_SYSTEM);
        }

        switch (commandWord) {

        case "debugdebugdebug": // DEBUG USE  ONLY
            return new DebugCommand();

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case GradebookAddCommand.COMMAND_WORD:
            return new GradebookAddCommandParser().parse(arguments);

        case GradebookListCommand.COMMAND_WORD:
            return new GradebookListCommand();

        case GradebookDeleteCommand.COMMAND_WORD:
            return new GradebookDeleteCommandParser().parse(arguments);

        case GradebookFindCommand.COMMAND_WORD:
            return new GradebookFindCommandParser().parse(arguments);

        case GradebookEditCommand.COMMAND_WORD:
            return new GradebookEditCommandParser().parse(arguments);

        case CourseAddCommand.COMMAND_WORD:
            return new CourseAddCommandParser().parse(arguments);

        case CourseEditCommand.COMMAND_WORD:
            return new CourseEditCommandParser().parse(arguments);

        case CourseListCommand.COMMAND_WORD:
            return new CourseListCommand();

        case CourseListStudentsCommand.COMMAND_WORD:
            return new CourseListStudentsCommand();

        case CourseDeleteCommand.COMMAND_WORD:
            return new CourseDeleteCommandParser().parse(arguments);

        case StudentAddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case NoteAddCommand.COMMAND_WORD:
            return new NoteAddCommandParser().parse(arguments);

        case NoteListCommand.COMMAND_WORD:
            return new NoteListCommandParser().parse(arguments);

        case NoteDeleteCommand.COMMAND_WORD:
            return new NoteDeleteCommandParser().parse(arguments);

        case NoteEditCommand.COMMAND_WORD:
            return new NoteEditCommandParser().parse(arguments);

        case NoteExportCommand.COMMAND_WORD:
            return new NoteExportCommandParser().parse(arguments);

        case NoteFindCommand.COMMAND_WORD:
            return new NoteFindCommandParser().parse(arguments);

        case StudentEditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case StudentFindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case StudentListCommand.COMMAND_WORD:
            return new StudentListCommand();

        case ModuleAddCommand.COMMAND_WORD:
            return new ModuleAddCommandParser().parse(arguments);

        case ModuleEditCommand.COMMAND_WORD:
            return new ModuleEditCommandParser().parse(arguments);

        case ModuleDeleteCommand.COMMAND_WORD:
            return new ModuleDeleteCommandParser().parse(arguments);

        case ModuleViewCommand.COMMAND_WORD:
            return new ModuleViewCommandParser().parse(arguments);

        case ModuleListCommand.COMMAND_WORD:
            return new ModuleListCommand();

        case ModuleFindCommand.COMMAND_WORD:
            return new ModuleFindCommandParser().parse(arguments);

        case ModuleEnrolCommand.COMMAND_WORD:
            return new ModuleEnrolCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ClassAddCommand.COMMAND_WORD:
            return new ClassAddCommandParser().parse(arguments);

        case ClassListCommand.COMMAND_WORD:
            return new ClassListCommand();

        case ClassListStudentAttendanceCommand.COMMAND_WORD:
            return new ClassListStudentAttendanceCommandParser().parse(arguments);

        case ClassEditCommand.COMMAND_WORD:
            return new ClassEditCommandParser().parse(arguments);

        case ClassDeleteCommand.COMMAND_WORD:
            return new ClassDeleteCommandParser().parse(arguments);

        case ClassDeleteStudentAttendanceCommand.COMMAND_WORD:
            return new ClassDeleteStudentAttendanceCommandParser().parse(arguments);

        case ClassAddStudentCommand.COMMAND_WORD:
            return new ClassAddStudentCommandParser().parse(arguments);

        case ClassAddStudentAttendanceCommand.COMMAND_WORD:
            return new ClassAddStudentAttendanceCommandParser().parse(arguments);

        case ClassDeleteStudentCommand.COMMAND_WORD:
            return new ClassDeleteStudentCommandParser().parse(arguments);

        case GradeAddCommand.COMMAND_WORD:
            return new GradeAddCommandParser().parse(arguments);

        case GradeListCommand.COMMAND_WORD:
            return new GradeListCommand();

        case GradeGraphCommand.COMMAND_WORD:
            return new GradeGraphCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
