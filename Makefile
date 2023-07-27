# Compiler settings
JAVAC = javac
JAVA = java
FLAGS = -Xlint:none
DIR = src/

# Java source files
SOURCES = $(wildcard *.java)

# Target executable
TARGET = JobScheduler

all: $(TARGET)
	@$(JAVA) $(TARGET) AnagramsCounterScheduler

$(TARGET): $(SOURCES)
	@echo "Compiling..."
	@$(JAVAC) $(FLAGS) $(SOURCES) > /dev/null 2>&1

clean:
	@rm -f $(TARGET) *.class

