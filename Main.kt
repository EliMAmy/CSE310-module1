// This is a simple Kotlin program to manage experiments. It allows users to add, view, and search, update for experiments based on their name.
data class Experiment(
    //This class represents an experiment with its details.
    val name: String,
    val description: String,
    val date: String,
    val temperature: Double,    
    val pH: Double,
    val treatmentTime: Double,
    val results: List<String>
)
fun clearScreen() {
    //This function clears the console screen by printing ANSI escape codes to move the cursor to the top-left corner and clear the screen.
    println("\u001b[H\u001b[2J")
    System.out.flush()
}
fun pressEnterToContinue() {
    //This function prompts the user to press Enter to continue. It waits for the user to press Enter before proceeding.
    println("Press Enter to continue...")
    readln()
}

//This function adds a new experiment to the list of experiments. It prompts the user for details and creates an Experiment object.
//If the user enters invalid input for temperature, pH, or treatment time, it defaults to 0.0.
//Every experiment is added to the list and a success message is printed.
fun addExperiment(experiments: MutableList<Experiment>) {
    clearScreen()
    println("-------------------------")
    println("Please, enter your experiment details:")
    print("Experiment Name: ")
    val name = readln()

    print("Description: ")
    val description = readln()

    print("Date: ")
    val date = readln()

    print("Temperature: ")
    val temperature: Double = readln().toDoubleOrNull() ?: 0.0

    print("pH: ")
    val pH: Double = readln().toDoubleOrNull() ?: 0.0

    print("Treatment Time (hours): ")
    val treatmentTime: Double = readln().toDoubleOrNull() ?: 0.0

    print("Results (comma-separated): ")
    val results: List<String> = readln()?.split(",")?.map { it.trim() } ?: emptyList()
    //An Experiment object is created with the provided details, and it is added to the list of experiments.
    val experiment = Experiment(
        name,
        description,
        date,
        temperature,
        pH,
        treatmentTime,
        results
    )
    //Then the experiment is added to the list of experiments and a success message is printed.
    experiments.add(experiment)
    println("-------------------------")
    clearScreen()
    println("Experiment $name added successfully!")

}

//This function displays all the experiments in the list. 
fun viewExperiments(experiments: List<Experiment>) {
    clearScreen()
    //If there are no experiments, it prints a message indicating that no experiments were found.
    if (experiments.isEmpty()) {
        println("No experiments found.")
        return
    }
    //Otherwise, it prints the details of each experiment in a formatted manner.
    println("-------------------------")
    println("View your recorded Experiments:")
    for ((index, experiment) in experiments.withIndex()) {
        println("${index + 1}. ${experiment.name} - ${experiment.description} - ${experiment.date}")
        println("   Temperature: ${experiment.temperature}°C, pH: ${experiment.pH}, Treatment Time: ${experiment.treatmentTime} hours")
        println("   Results: ${experiment.results.joinToString(", ")}")
    }
}

//This function searches for experiments by name.
fun searchExperiment(experiments: List<Experiment>) {
    clearScreen()
    //It prompts the user to enter the name of the experiment they want to search for.
    print("Enter the experiment name to search: ")
    val searchName = readln()
    //Then it filters the list of experiments to find those whose names contain the search term, ignoring case.
    val foundExperiments = experiments.filter {
        it.name.contains(searchName, ignoreCase = true)
    }
    //If no experiments are found, it prints a message indicating that no experiments were found.
    if (foundExperiments.isEmpty()) {
        println("No experiment found!")
        return
    }
    //Otherwise, it prints the details of each found experiment in a formatted manner.
    println("-------------------------")
    println("Experiment(s) found:")
    //It prints a separator line and the details of each found experiment, including name, description, date, temperature, pH, treatment time, and results.
    for (experiment in foundExperiments) {
        println("-------------------------")
        println("Name: ${experiment.name}")
        println("Description: ${experiment.description}")
        println("Date: ${experiment.date}")
        println("Temperature: ${experiment.temperature}°C")
        println("pH: ${experiment.pH}")
        println("Treatment Time: ${experiment.treatmentTime} hours")
        println("Results: ${experiment.results.joinToString(", ")}")
        println("-------------------------")
    }
}

//This function updates an existing experiment in the list.
fun updateExperiment(experiments: MutableList<Experiment>) {
    clearScreen()
    println("-------------------------")
    println("Choose an experiment to update:")
    //The user is prompted to enter the number of the experiment they want to update.
    for ((index, experiment) in experiments.withIndex()) {
        println("${index + 1}. ${experiment.name}")
    }
    print("Enter the number of the experiment to update: ")
    val updateIndex = readln().toIntOrNull()
    //If the user enters a valid number corresponding to an experiment, 
    //the program prompts the user to enter new values for each field of the experiment. 
    if (updateIndex != null && updateIndex in 1..experiments.size) {
        val experiment = experiments[updateIndex - 1]
        println("Updating experiment '${experiment.name}'")
        print("New Name (leave blank to keep current): ")
        val newName = readln().ifBlank { experiment.name }
        print("New Description (leave blank to keep current): ")
        val newDescription = readln().ifBlank { experiment.description }
        print("New Date (leave blank to keep current): ")
        val newDate = readln().ifBlank { experiment.date }
        print("New Temperature (leave blank to keep current): ")
        val newTemperature = readln().toDoubleOrNull() ?: experiment.temperature
        print("New pH (leave blank to keep current): ")
        val newPH = readln().toDoubleOrNull() ?: experiment.pH
        print("New Treatment Time (leave blank to keep current): ")
        val newTreatmentTime = readln().toDoubleOrNull() ?: experiment.treatmentTime
        print("New Results (comma-separated, leave blank to keep current): ")
        val newResultsInput = readln()
        //If the user leaves the input blank, the current results are kept; otherwise, the new results are split into a list.
        val newResults = if (newResultsInput.isBlank()) experiment.results else newResultsInput.split(",").map { it.trim() }
        //Finally, the experiment in the list is updated with the new values, and a success message is printed.
        experiments[updateIndex - 1] = Experiment(newName, newDescription, newDate, newTemperature, newPH, newTreatmentTime, newResults)
        clearScreen()
        println("-------------------------")
        println("Experiment '${experiment.name}' updated successfully!")
    } else {
        println("Invalid experiment number.")
    }
}


//This function deletes an experiment from the list. 
//It prompts the user to choose an experiment to delete by displaying a numbered list of experiments.
fun deleteExperiment(experiments: MutableList<Experiment>) {
    clearScreen()
    println("-------------------------")
    println("Choose an experiment to delete:")
    // the user is prompted to enter the number of the experiment they want to delete. 
    for ((index, experiment) in experiments.withIndex()) {
        println("${index + 1}. ${experiment.name}")
    }
    print("Enter the number of the experiment to delete: ")
    val deleteIndex = readln().toIntOrNull()
    //If the user enters a valid number corresponding to an experiment, the experiment is removed from the list and a success message is printed.
    if (deleteIndex != null && deleteIndex in 1..experiments.size) {
        val experiment = experiments[deleteIndex - 1]
        experiments.removeAt(deleteIndex - 1)
        clearScreen()
        println("-------------------------")
        println("Experiment '${experiment.name}' deleted successfully!")
    } 
    //If the user enters an invalid number, an error message is printed indicating that the experiment number is invalid.
    else {
        println("Invalid experiment number.")
    }
}

// This is the main function that serves as the entry point of the program. 
// It initializes a mutable list of experiments and enters an infinite loop to display a menu of options to the user.
fun main() {
    val experiments = mutableListOf<Experiment>()
    while (true) {
        clearScreen()
        println("Welcome to the Experiment Management System!")
        println("Please choose an option:")
        println("1. Add Experiment")
        println("2. View Experiments")
        println("3. Search Experiment")
        println("4. Update Experiment")
        println("5. Delete Experiment")
        println("6. Exit")
        print("Enter your choice (1-6): ")
        val choice = readln()
        when (choice) {
            "1" -> {addExperiment(experiments)
                pressEnterToContinue()}
            "2" -> {viewExperiments(experiments)
                pressEnterToContinue()}
            "3" -> {searchExperiment(experiments)
                pressEnterToContinue()}
            "4" -> {updateExperiment(experiments)
                pressEnterToContinue()}
            "5" -> {deleteExperiment(experiments)
                pressEnterToContinue()}
            "6" -> {
                clearScreen()
                println("Thank you for your usage of the Experiment Management System! Goodbye!")
                return
            }
            else -> println("Oh no! Please try again.")
        }
    }
    
}