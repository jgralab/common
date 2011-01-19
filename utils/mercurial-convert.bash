#!/bin/bash -e

if [[ -z ${1} || -z ${2} || -z ${3} ]]; then
    echo "Usage: mercurial-convert.bash svn-dir hg-dir project-path"
    echo "       <project-path>: First dir below Repository Root, like /jgralab."
    exit 0
fi

# The first path element below the repository root, like /jgralab
srcdir=$1
destdir=$2
projectpath=$3

authormap=$(mktemp)
branchmap=$(mktemp)
splicemap=$(mktemp)
uuid=$(svn info "${srcdir}" | grep "UUID" | sed -r -e 's/^[^:]+: //')

echo "Repository UUID is ${uuid}"

echo
if [[ -d ${destdir} ]]; then
    echo "Destination directory already exist."
    echo "Update it?"
    echo "Confirm with RET or abort with ^C."
    read
else
    echo "Going to create destination directory ${destdir}."
    echo "Confirm with RET or abort with ^C."
    read
    mkdir ${destdir}
fi

# Then the history contains complete names and addresses instead of only user
# names.
cat > "${authormap}" << AUTHORMAP
abaldauf=Arne Baldauf <abaldauf@uni-koblenz.de>
afuhr=Andreas Fuhr <afuhr@uni-koblenz.de>
brack=Thomas Brack <brack@uni-koblenz.de>
danijank=Daniel Janke <danijank@uni-koblenz.de>
dbildh=Daniel Bildhauer <dbildh@uni-koblenz.de>
ebert=Jürgen Ebert <ebert@uni-koblenz.de>
eziegler=Elisa Ziegler <eziegler@uni-koblenz.de>
falke=Kerstin Falkowski <falke@uni-koblenz.de>
gcatellani=Grégory Catellani <gcatellani@uni-koblenz.de>
groener=Gerd Groener <groener@uni-koblenz.de>
heimdall=Tassilo Horn <horn@uni-koblenz.de>
horn=Tassilo Horn <horn@uni-koblenz.de>
hschwarz=Hannes Schwarz <hschwarz@uni-koblenz.de>
juhaas=Judith Haas <juhaas@uni-koblenz.de>
kheckelmann=Kristina Heckelmann <kheckelmann@uni-koblenz.de>
klaasd=Klaas Dellschaft <klaasd@uni-koblenz.de>
manesh=Mahdi Derakhshanmanesh <manesh@uni-koblenz.de>
mmce=Eckhard Großmann <mmce@uni-koblenz.de>
monte=José Angel Monte Barreto <monte@uni-koblenz.de>
msattel=Matthias Sattel <msattel@uni-koblenz.de>
riediger=Volker Riediger <riediger@uni-koblenz.de>
splitt=Sebastian Plitt <splitt@uni-koblenz.de>
strauss=Sascha Strauß <strauss@uni-koblenz.de>
ultbreit=Nicolas Vika <ultbreit@uni-koblenz.de>
walter=Tobias Walter <walter@uni-koblenz.de>
weyandc=Christian Weyand <weyandc@uni-koblenz.de>
winter=Andreas Winter <winter@se.uni-oldenburg.de>
wojke=Philipp Wojke <wojke@uni-koblenz.de>
AUTHORMAP

echo
echo "Using this authormap:"
cat "${authormap}"
echo "Confirm with RET or abort with ^C."
read

# This can be used to rename branches:
#   old_branch_name new_branch_name
# or erase named branch markers:
#   old_branch_name default
cat > "${branchmap}" << BRANCHMAP
BRANCHMAP

echo
echo "Using this branchmap:"
cat "${branchmap}"
echo "Confirm with RET or abort with ^C."
read

# This tells the mercurial import manually where we merged from one branch into
# another:
#   merged_revision parent_revision_1,parent_revision_2
if [[ ${projectpath} == '/jgralab' ]]; then
    cat > "${splicemap}" << SPLICEMAP
svn:${uuid}${projectpath}/branches/algolib@2845 svn:${uuid}${projectpath}/branches/algolib@2841,svn:${uuid}${projectpath}/trunk@2839
svn:${uuid}${projectpath}/branches/algolib@2856 svn:${uuid}${projectpath}/branches/algolib@2855,svn:${uuid}${projectpath}/trunk@2851
svn:${uuid}${projectpath}/branches/algolib@2861 svn:${uuid}${projectpath}/branches/algolib@2860,svn:${uuid}${projectpath}/trunk@2858
svn:${uuid}${projectpath}/branches/algolib@2900 svn:${uuid}${projectpath}/branches/algolib@2885,svn:${uuid}${projectpath}/trunk@2895
svn:${uuid}${projectpath}/branches/algolib@2956 svn:${uuid}${projectpath}/branches/algolib@2911,svn:${uuid}${projectpath}/trunk@2951
svn:${uuid}${projectpath}/branches/algolib@2999 svn:${uuid}${projectpath}/branches/algolib@2998,svn:${uuid}${projectpath}/trunk@2997
svn:${uuid}${projectpath}/branches/algolib@3043 svn:${uuid}${projectpath}/branches/algolib@3041,svn:${uuid}${projectpath}/trunk@3033
svn:${uuid}${projectpath}/branches/algolib@3059 svn:${uuid}${projectpath}/branches/algolib@3058,svn:${uuid}${projectpath}/trunk@3055
svn:${uuid}${projectpath}/branches/algolib@3073 svn:${uuid}${projectpath}/branches/algolib@3072,svn:${uuid}${projectpath}/trunk@3069
svn:${uuid}${projectpath}/branches/algolib@3115 svn:${uuid}${projectpath}/branches/algolib@3112,svn:${uuid}${projectpath}/trunk@3114
svn:${uuid}${projectpath}/branches/algolib@3149 svn:${uuid}${projectpath}/branches/algolib@3115,svn:${uuid}${projectpath}/trunk@3148
svn:${uuid}${projectpath}/branches/algolib@3178 svn:${uuid}${projectpath}/branches/algolib@3175,svn:${uuid}${projectpath}/trunk@3176
svn:${uuid}${projectpath}/branches/algolib@3187 svn:${uuid}${projectpath}/branches/algolib@3277,svn:${uuid}${projectpath}/trunk@3286
svn:${uuid}${projectpath}/branches/algolib@3511 svn:${uuid}${projectpath}/branches/algolib@3501,svn:${uuid}${projectpath}/trunk@3509
svn:${uuid}${projectpath}/branches/algolib@3531 svn:${uuid}${projectpath}/branches/algolib@3525,svn:${uuid}${projectpath}/trunk@3530
svn:${uuid}${projectpath}/branches/algolib@3553 svn:${uuid}${projectpath}/branches/algolib@3542,svn:${uuid}${projectpath}/trunk@3545
svn:${uuid}${projectpath}/branches/parallel-greql@3375 svn:${uuid}${projectpath}/branches/parallel-greql@3374,svn:${uuid}${projectpath}/trunk@3373
svn:${uuid}${projectpath}/branches/parallel-greql@3407 svn:${uuid}${projectpath}/branches/parallel-greql@3406,svn:${uuid}${projectpath}/trunk@3405
svn:${uuid}${projectpath}/branches/parallel-greql@3450 svn:${uuid}${projectpath}/branches/parallel-greql@3449,svn:${uuid}${projectpath}/trunk@3447
svn:${uuid}${projectpath}/branches/parallel-greql@3467 svn:${uuid}${projectpath}/branches/parallel-greql@3458,svn:${uuid}${projectpath}/trunk@3457
svn:${uuid}${projectpath}/branches/parallel-greql@3494 svn:${uuid}${projectpath}/branches/parallel-greql@3493,svn:${uuid}${projectpath}/trunk@3483
svn:${uuid}${projectpath}/branches/parallel-greql@3527 svn:${uuid}${projectpath}/branches/parallel-greql@3526,svn:${uuid}${projectpath}/trunk@3524
SPLICEMAP
fi

echo "Using this splicemap:"
cat "${splicemap}"
echo "Confirm with RET or abort with ^C."
read

echo
echo "Ok, I'm going to convert ${srcdir} to ${destdir}..."
echo "Confirm with RET or abort with ^C."
read

hg convert -v \
    --authors "${authormap}" \
    --splicemap "${splicemap}" \
    --branchmap "${branchmap}" \
    --datesort \
    "${srcdir}" \
    "${destdir}"

echo "Fini."

