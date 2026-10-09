package com.example.studyjams

/** A single multiple-choice question. [correctIndex] points into [options]. */
data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

/** A quiz level (category) made of several [Question]s. */
data class Level(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<Question>
)

private fun q(text: String, correctIndex: Int, vararg options: String) =
    Question(text, options.toList(), correctIndex)

/** All quiz content lives here so it is easy to edit or extend. */
object QuizData {

    val levels: List<Level> = listOf(
        Level(
            id = "beginner",
            title = "Beginner's Level",
            description = "The easy level - technical basics to get you started.",
            questions = listOf(
                q("Which language is used for native Android development?", 0,
                    "Kotlin", "PHP", "Ruby", "Swift"),
                q("What does CPU stand for?", 0,
                    "Central Processing Unit", "Computer Personal Unit",
                    "Central Program Utility", "Control Processing Unit"),
                q("Which language structures webpages?", 1,
                    "CSS", "HTML", "SQL", "Python"),
                q("Which company owns Android?", 1,
                    "Apple", "Google", "Meta", "Microsoft"),
                q("Which language styles webpages?", 0,
                    "CSS", "Java", "C++", "SQL")
            )
        ),
        Level(
            id = "intermediate",
            title = "Intermediate Level",
            description = "The medium level - Android, data structures and web concepts.",
            questions = listOf(
                q("Which Android component stores UI state?", 0,
                    "ViewModel", "Intent", "Gradle", "Manifest"),
                q("Which data structure follows FIFO?", 1,
                    "Stack", "Queue", "Tree", "Graph"),
                q("Which HTTP method updates part of a resource?", 2,
                    "GET", "POST", "PATCH", "TRACE"),
                q("Which JavaScript feature handles asynchronous results?", 0,
                    "Promise", "Loop", "Class", "Array"),
                q("Which data structure uses LIFO?", 1,
                    "Queue", "Stack", "Graph", "Heap")
            )
        ),
        Level(
            id = "expert",
            title = "Expert Level",
            description = "The hard level - concurrency, algorithms, security and databases.",
            questions = listOf(
                q("Which Kotlin construct manages asynchronous tasks?", 0,
                    "Coroutines", "Interfaces", "Arrays", "Enums"),
                q("Which algorithm finds shortest paths in weighted graphs with non-negative edges?", 2,
                    "DFS", "BFS", "Dijkstra", "Merge Sort"),
                q("Which protocol secures HTTP communication?", 1,
                    "FTP", "TLS", "SMTP", "DNS"),
                q("Which database property guarantees atomic transactions?", 2,
                    "Isolation", "Durability", "Atomicity", "Consistency"),
                q("Which technique reduces repeated database queries?", 0,
                    "Caching", "Polling", "Recursion", "Compilation")
            )
        ),
        Level(
            id = "popculture",
            title = "Pop Culture Quiz",
            description = "Movies and music - how well do you know the stars?",
            questions = listOf(
                q("Who played Iron Man in the Marvel Cinematic Universe?", 1,
                    "Chris Evans", "Robert Downey Jr.", "Chris Hemsworth", "Tom Holland"),
                q("Who plays the female lead in the upcoming movie Varanasi?", 2,
                    "Deepika Padukone", "Alia Bhatt", "Priyanka Chopra Jonas", "Shraddha Kapoor"),
                q("Which fictional character is known for saying \u201cI'll be back\u201d?", 1,
                    "Superman", "Terminator", "Batman", "James Bond"),
                q("Which of these singers has won the most Grammy Awards?", 2,
                    "Taylor Swift", "Ariana Grande", "Beyonc\u00e9", "Sabrina Carpenter"),
                q("Which artists are popularly known as the \u201cKing and Queen of Pop\u201d?", 1,
                    "Elvis Presley and Adele", "Michael Jackson and Madonna",
                    "Bruno Mars and Beyonc\u00e9", "Justin Bieber and Taylor Swift")
            )
        ),
        Level(
            id = "sports",
            title = "Sports Quiz",
            description = "Cricket, football, F1 and more - test your sports knowledge.",
            questions = listOf(
                q("In Formula 1, what does DRS stand for?", 0,
                    "Drag Reduction System", "Driver Racing Speed",
                    "Dynamic Rotation System", "Drag Racing Setup"),
                q("Which country won the ICC Men\u2019s T20 World Cup in 2024?", 1,
                    "Australia", "India", "England", "Pakistan"),
                q("How many players from one team are on the field in football?", 2,
                    "9", "10", "11", "12"),
                q("Who is known as the \u201cFlying Sikh\u201d of India?", 1,
                    "Neeraj Chopra", "Milkha Singh", "Abhinav Bindra", "P. T. Usha"),
                q("Which sport is associated with the Davis Cup?", 0,
                    "Tennis", "Cricket", "Hockey", "Badminton")
            )
        )
    )

    fun byId(id: String?): Level? = levels.firstOrNull { it.id == id }
}
