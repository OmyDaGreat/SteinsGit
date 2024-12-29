package xyz.malefic.git

import java.io.Serializable

/** Data class representing a Git repository. */
data class GitRepository(
    /** Name of the repository */
    val name: String,
    /** Local file system path to the repository */
    val path: String,
    /** Currently checked-out branch */
    var currentBranch: String,
    /** Remote origin URL (if any) */
    var remoteUrl: String? = null,
    /** Indicates if there are uncommited changes */
    var isDirty: Boolean = false,
    /** Indicates whether to automatically update or not */
    var autoUpdate: Boolean = false,
) : Serializable
