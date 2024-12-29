import co.touchlab.kermit.Logger
import java.util.prefs.Preferences

fun main() {
    val userRoot = Preferences.userRoot()

    // Clear all preferences under the "Common" node
    val commonNode = userRoot.node("steins;git")
    clearPreferences(commonNode)

    Logger.d("User Preferences Tree:")
    printPreferencesTree(userRoot, 0)
}

/**
 * Recursively prints the preferences tree starting from the given node.
 *
 * @param node The root preferences node to start the traversal.
 * @param depth The depth of the current node in the tree, used for indentation.
 */
fun printPreferencesTree(
    node: Preferences,
    depth: Int,
) {
    val indent = "  ".repeat(depth)

    // Print the current node's name
    Logger.d("$indent- ${node.name()}")

    // Print the key-value pairs in the current node
    try {
        node.keys().forEach { key ->
            val value = node.get(key, "<no value>")
            Logger.d("$indent  $key = $value")
        }
    } catch (e: Exception) {
        Logger.d("$indent  [Error reading keys: ${e.message}]")
    }

    // Recursively process child nodes
    try {
        node.childrenNames().forEach { childName ->
            printPreferencesTree(node.node(childName), depth + 1)
        }
    } catch (e: Exception) {
        Logger.d("$indent  [Error reading children: ${e.message}]")
    }
}

/**
 * Recursively clears all preferences starting from the given node.
 *
 * @param node The root preferences node to start the clearing.
 */
fun clearPreferences(node: Preferences) {
    // Clear all keys in the current node
    try {
        node.keys().forEach { key -> node.remove(key) }
    } catch (e: Exception) {
        Logger.d("Error clearing keys in node '${node.name()}': ${e.message}")
    }

    // Recursively clear child nodes
    try {
        node.childrenNames().forEach { childName ->
            clearPreferences(node.node(childName))
            node.node(childName).removeNode()
        }
        node.flush() // Ensure changes are persisted
    } catch (e: Exception) {
        Logger.d("Error clearing children in node '${node.name()}': ${e.message}")
    }
}
