package th.co.dtac.library

/**
 * Created by PrewSitthirat on 6/11/2020 AD.
 */

class DtacWidget {

    companion object {

        private var language: String = "EN"

        @JvmStatic
        fun initial(lang: String) {
            language = lang
        }

        @JvmStatic
        fun currentLanguage(): String = language
    }
}