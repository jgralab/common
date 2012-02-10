# The Common Project

This projects contains some utilities used by jgralab itself and various
jgralab projects.  Especially, almost all jgralab projects make use of the
`common.xml` ant file for building.  In order to have that working correctly,
all jgralab projects need to reside in some base folder like so:

    jgsrc/               # your jgralab workspace
    +-> common/          # this project
    +-> jgralab/         # jgralab itself
    +-> foobar/          # some more jgralab projects
    +-> ...
    `-> barfoo/

## License

Copyright (C) 2007-2012 The JGraLab Team <ist@uni-koblenz.de>

Distributed under the General Public License (Version 3), with the following
additional grant:

    Additional permission under GNU GPL version 3 section 7

    If you modify this Program, or any covered work, by linking or combining it
    with Eclipse (or a modified version of that program or an Eclipse plugin),
    containing parts covered by the terms of the Eclipse Public License (EPL),
    the licensors of this Program grant you additional permission to convey the
    resulting work.  Corresponding Source for a non-source form of such a
    combination shall include the source code for the parts of JGraLab used as
    well as that of the covered work.


<!-- Local Variables:        -->
<!-- mode: markdown          -->
<!-- indent-tabs-mode: nil   -->
<!-- End:                    -->
