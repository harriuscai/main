package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.classroom.ClassroomManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleManager;
import seedu.address.model.person.Person;
import seedu.address.model.student.StudentManager;

/**
 * Unassigns an existing student from an existing class.
 */
public class ClassDeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "class delstudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a student from a class"
            + " for the system. "
            + "Parameters: "
            + PREFIX_CLASS_NAME + "CLASS_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_MATRIC + "MATRIC_NO\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_NAME + "T16 "
            + PREFIX_MODULE_CODE + "CG1111 "
            + PREFIX_MATRIC + "A6942069M";

    public static final String MESSAGE_SUCCESS = "Student unassigned from class: %1$s"
            + ", Class: %2$s"
            + ", Module code: %3$s";
    public static final String MESSAGE_FAIL = "Class belonging to module not found!";

    public static final String MESSAGE_CLASSROOM_STUDENT_NOT_FOUND = "This student doesn't belong to class: %1$s";
    public static final String MESSAGE_INVALID_STUDENT = "Student does not exist";
    public static final String MESSAGE_MODULE_CODE_INVALID = "Module code does not exist";
    public static final String MESSAGE_INVALID_STUDENT_MODULE = "Student is not enrolled in module!";

    private Classroom classToUnassignStudent;
    private final String className;
    private final String moduleCode;
    private final String matricNo;

    public ClassDeleteStudentCommand(String className, String moduleCode, String matricNo) {
        requireAllNonNull(className, moduleCode, matricNo);
        this.className = className;
        this.moduleCode = moduleCode;
        this.matricNo = matricNo;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ClassroomManager classroomManager = ClassroomManager.getInstance();
        ModuleManager moduleManager = ModuleManager.getInstance();
        StudentManager studentManager = StudentManager.getInstance();

        if (!moduleManager.doesModuleExist(moduleCode)) {
            throw new CommandException(MESSAGE_MODULE_CODE_INVALID);
        }

        if (!studentManager.doesStudentExistForGivenMatricNo(matricNo)) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }

        Module module = moduleManager.getModuleByModuleCode(moduleCode);
        Person student = studentManager.retrieveStudentByMatricNo(matricNo);
        if (!moduleManager.isStudentEnrolledInModule(module, student)) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_MODULE);
        }

        classToUnassignStudent = classroomManager.findClassroom(className, moduleCode);
        if (classToUnassignStudent == null) {
            throw new CommandException(MESSAGE_FAIL);
        }

        if (!classroomManager.hasClassroomStudent(classToUnassignStudent, matricNo)) {
            throw new CommandException(String.format(MESSAGE_CLASSROOM_STUDENT_NOT_FOUND, matricNo));
        }

        classroomManager.unassignStudent(classToUnassignStudent, matricNo);
        classroomManager.saveClassroomList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, matricNo,
                classToUnassignStudent.getClassName(), classToUnassignStudent.getModuleCode()),
                classroomManager.getClassroomHtmlRepresentation());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassDeleteStudentCommand // instanceof handles nulls
                && className.equals(((ClassDeleteStudentCommand) other).className)
                && moduleCode.equals(((ClassDeleteStudentCommand) other).moduleCode)
                && matricNo.equals(((ClassDeleteStudentCommand) other).matricNo));

    }
}
