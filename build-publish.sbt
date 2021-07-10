import sbt.url
import xerial.sbt.Sonatype.GitHubHosting

usePgpKeyHex("CC49A6531877B8009FA716C0089CB57A8A4BDD97")

ThisBuild / sonatypeProfileName := "com.jackson42"
ThisBuild / sonatypeProjectHosting := Some(GitHubHosting("PierreAdam", "play-jooq", "adam.pierre.dany@gmail.com"))
ThisBuild / publishTo := sonatypePublishToBundle.value
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishMavenStyle := true

ThisBuild / credentials ++= Seq(
  Path.userHome / ".sbt" / "sonatype_credentials"
).toStream
  .filter(file => file.exists())
  .map(file => Credentials(file))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/pierreadam/play-jooq"),
    "scm:git@github.com:PierreAdam/play-jooq.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "PierreAdam",
    name = "Pierre Adam",
    email = "adam.pierre.dany@gmail.com",
    url = url("https://github.com/PierreAdam")
  )
)
