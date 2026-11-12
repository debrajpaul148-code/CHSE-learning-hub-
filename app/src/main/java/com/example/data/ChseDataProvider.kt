package com.example.data

import com.example.model.*

object ChseDataProvider {

  // Subjects for Class 11 and Class 12
  val subjects: List<Subject> = listOf(
    // Class 12
    Subject(
      id = "phy_12",
      name = "Physics",
      odiaName = "ପଦାର୍ଥ ବିଜ୍ଞାନ",
      classLevel = 12,
      iconType = "bolt",
      colorHex = 0xFF5B21B6,
      description = "CHSE Bureau +2 Physics: Electrostatics, Optics, Magnetism, Modern Physics & Semiconductor Electronics",
      chaptersCount = 14,
      completedChaptersCount = 6
    ),
    Subject(
      id = "chem_12",
      name = "Chemistry",
      odiaName = "ରସାୟନ ବିଜ୍ଞାନ",
      classLevel = 12,
      iconType = "science",
      colorHex = 0xFF0284C7,
      description = "CHSE Bureau +2 Chemistry: Physical, Inorganic & Organic Chemistry (Solutions, Kinetics, Coordination, Aldehydes)",
      chaptersCount = 13,
      completedChaptersCount = 5
    ),
    Subject(
      id = "math_12",
      name = "Mathematics",
      odiaName = "ଗଣିତ",
      classLevel = 12,
      iconType = "calculate",
      colorHex = 0xFF10B981,
      description = "CHSE Bureau Elements of Mathematics: Calculus, Matrices, Determinants, Vectors, 3D Geometry & Probability",
      chaptersCount = 12,
      completedChaptersCount = 7
    ),
    Subject(
      id = "bio_12",
      name = "Biology",
      odiaName = "ଜୀବ ବିଜ୍ଞାନ",
      classLevel = 12,
      iconType = "eco",
      colorHex = 0xFF16A34A,
      description = "CHSE Bureau +2 Biology: Botany (Plant Reproduction, Genetics) & Zoology (Human Reproduction, Health & Disease)",
      chaptersCount = 16,
      completedChaptersCount = 8
    ),
    Subject(
      id = "eng_12",
      name = "English",
      odiaName = "ଇଂରାଜୀ",
      classLevel = 12,
      iconType = "menu_book",
      colorHex = 0xFFEA580C,
      description = "CHSE Invitation to English 1, 2, 3, 4: Reading Comprehension, Grammar, Report & Formal Writing",
      chaptersCount = 10,
      completedChaptersCount = 4
    ),
    Subject(
      id = "odia_12",
      name = "MIL Odia",
      odiaName = "ମାତୃଭାଷା ଓଡ଼ିଆ",
      classLevel = 12,
      iconType = "auto_stories",
      colorHex = 0xFFD97706,
      description = "CHSE ସାହିତ୍ୟ ଜ୍ୟୋତି ଦ୍ୱାଦଶ ଶ୍ରେଣୀ: ଗଦ୍ୟ, ପଦ୍ୟ, ପ୍ରବନ୍ଧ, ଏକାଙ୍କିକା ଏବଂ ବ୍ୟାକରଣ",
      chaptersCount = 11,
      completedChaptersCount = 6
    ),

    // Class 11
    Subject(
      id = "phy_11",
      name = "Physics",
      odiaName = "ପଦାର୍ଥ ବିଜ୍ଞାନ (ଏକାଦଶ)",
      classLevel = 11,
      iconType = "bolt",
      colorHex = 0xFF5B21B6,
      description = "Kinematics, Laws of Motion, Work, Energy & Power, Gravitation, Thermodynamics, Oscillations & Waves",
      chaptersCount = 14,
      completedChaptersCount = 9
    ),
    Subject(
      id = "chem_11",
      name = "Chemistry",
      odiaName = "ରସାୟନ ବିଜ୍ଞାନ (ଏକାଦଶ)",
      classLevel = 11,
      iconType = "science",
      colorHex = 0xFF0284C7,
      description = "Basic Concepts, Structure of Atom, Periodic Classification, Chemical Bonding, Equilibrium, Organic Hydrocarbons",
      chaptersCount = 12,
      completedChaptersCount = 8
    ),
    Subject(
      id = "math_11",
      name = "Mathematics",
      odiaName = "ଗଣିତ (ଏକାଦଶ)",
      classLevel = 11,
      iconType = "calculate",
      colorHex = 0xFF10B981,
      description = "Sets & Relations, Trigonometric Functions, Complex Numbers, Binomial Theorem, Straight Lines, Limits",
      chaptersCount = 14,
      completedChaptersCount = 10
    ),
    Subject(
      id = "bio_11",
      name = "Biology",
      odiaName = "ଜୀବ ବିଜ୍ଞାନ (ଏକାଦଶ)",
      classLevel = 11,
      iconType = "eco",
      colorHex = 0xFF16A34A,
      description = "Diversity in Living World, Cell Biology, Biomolecules, Plant Physiology & Human Physiology",
      chaptersCount = 15,
      completedChaptersCount = 7
    ),
    Subject(
      id = "eng_11",
      name = "English",
      odiaName = "ଇଂରାଜୀ (ଏକାଦଶ)",
      classLevel = 11,
      iconType = "menu_book",
      colorHex = 0xFFEA580C,
      description = "Invitation to English - Prose, Poetry, Short Stories and Writing Skills",
      chaptersCount = 8,
      completedChaptersCount = 5
    ),
    Subject(
      id = "odia_11",
      name = "MIL Odia",
      odiaName = "ମାତୃଭାଷା ଓଡ଼ିଆ (ଏକାଦଶ)",
      classLevel = 11,
      iconType = "auto_stories",
      colorHex = 0xFFD97706,
      description = "ସାହିତ୍ୟ ଜ୍ୟୋତି ଏକାଦଶ ଶ୍ରେଣୀ: ପ୍ରାଚୀନ ଓ ମଧ୍ୟଯୁଗୀୟ ପଦ୍ୟ, ଗଦ୍ୟ ଓ ବ୍ୟାକରଣ",
      chaptersCount = 9,
      completedChaptersCount = 6
    )
  )

  // Chapters list
  val chapters: List<Chapter> = listOf(
    // Class 12 Physics
    Chapter(
      id = "ch_phy12_1",
      subjectId = "phy_12",
      chapterNumber = 1,
      title = "Electric Charges and Fields",
      odiaTitle = "ବୈଦ୍ୟୁତିକ ଆଧାନ ଏବଂ କ୍ଷେତ୍ର",
      classLevel = 12,
      weightageMarks = 8,
      summary = "Coulomb's Law, Electric Dipole, Gauss's Theorem and its applications to calculate electric field due to infinite wire, plane sheet, and spherical shell.",
      isCompleted = true,
      progressPercent = 100
    ),
    Chapter(
      id = "ch_phy12_2",
      subjectId = "phy_12",
      chapterNumber = 2,
      title = "Electrostatic Potential and Capacitance",
      odiaTitle = "ସ୍ଥିରବୈଦ୍ୟୁତିକ ବିଭବ ଓ ଧାରକତ୍ୱ",
      classLevel = 12,
      weightageMarks = 7,
      summary = "Electric potential due to a point charge & dipole, equipotential surfaces, energy of a dipole, capacitance of parallel plate capacitor with dielectric medium.",
      isCompleted = true,
      progressPercent = 85
    ),
    Chapter(
      id = "ch_phy12_3",
      subjectId = "phy_12",
      chapterNumber = 3,
      title = "Current Electricity",
      odiaTitle = "ଚଳ ବିଦ୍ୟୁତ",
      classLevel = 12,
      weightageMarks = 8,
      summary = "Ohm's law, drift velocity, Kirchhoff's laws, Wheatstone bridge, meter bridge, potentiometer and internal resistance comparison.",
      isCompleted = true,
      progressPercent = 90
    ),
    Chapter(
      id = "ch_phy12_4",
      subjectId = "phy_12",
      chapterNumber = 4,
      title = "Moving Charges and Magnetism",
      odiaTitle = "ଗତିଶୀଳ ଆଧାନ ଏବଂ ଚୁମ୍ବକତ୍ୱ",
      classLevel = 12,
      weightageMarks = 6,
      summary = "Biot-Savart law, Ampere's circuital law, force on moving charge in magnetic field, cyclotron, torque on rectangular coil, galvanometer conversion.",
      isCompleted = false,
      progressPercent = 45
    ),
    Chapter(
      id = "ch_phy12_5",
      subjectId = "phy_12",
      chapterNumber = 5,
      title = "Ray Optics and Optical Instruments",
      odiaTitle = "ରଶ୍ମି ଆଲୋକ ବିଜ୍ଞାନ ଓ ଆଲୋକୀୟ ଉପକରଣ",
      classLevel = 12,
      weightageMarks = 9,
      summary = "Refraction at spherical surfaces, Lens maker's formula, total internal reflection, prism dispersion, astronomical telescope and compound microscope.",
      isCompleted = false,
      progressPercent = 30
    ),
    Chapter(
      id = "ch_phy12_6",
      subjectId = "phy_12",
      chapterNumber = 6,
      title = "Semiconductor Electronics",
      odiaTitle = "ଅର୍ଦ୍ଧପରିବାହୀ ଇଲେକ୍ଟ୍ରୋନିକ୍ସ",
      classLevel = 12,
      weightageMarks = 7,
      summary = "Energy bands in conductors, semiconductors & insulators, p-n junction diode, rectifiers (half-wave & full-wave), Zener diode, logic gates.",
      isCompleted = false,
      progressPercent = 0
    ),

    // Class 12 Chemistry
    Chapter(
      id = "ch_chem12_1",
      subjectId = "chem_12",
      chapterNumber = 1,
      title = "Solutions",
      odiaTitle = "ଦ୍ରବଣ",
      classLevel = 12,
      weightageMarks = 7,
      summary = "Types of solutions, Raoult's law, Colligative properties (Elevation in boiling point, Depression in freezing point, Osmotic pressure), Van't Hoff factor.",
      isCompleted = true,
      progressPercent = 100
    ),
    Chapter(
      id = "ch_chem12_2",
      subjectId = "chem_12",
      chapterNumber = 2,
      title = "Electrochemistry",
      odiaTitle = "ବିଦ୍ୟୁତ ରସାୟନ",
      classLevel = 12,
      weightageMarks = 8,
      summary = "Galvanic cells, Nernst equation, conductance in electrolytic solutions, Kohlrausch's law, Faraday's laws of electrolysis, batteries & fuel cells.",
      isCompleted = true,
      progressPercent = 80
    ),
    Chapter(
      id = "ch_chem12_3",
      subjectId = "chem_12",
      chapterNumber = 3,
      title = "Chemical Kinetics",
      odiaTitle = "ରାସାୟନିକ ଗତିବିଜ୍ଞାନ",
      classLevel = 12,
      weightageMarks = 7,
      summary = "Rate of reaction, factors affecting rate, order and molecularity of reaction, integrated rate equations for zero and first order, Arrhenius equation.",
      isCompleted = false,
      progressPercent = 60
    ),
    Chapter(
      id = "ch_chem12_4",
      subjectId = "chem_12",
      chapterNumber = 4,
      title = "Aldehydes, Ketones and Carboxylic Acids",
      odiaTitle = "ଆଲଡିହାଇଡ୍, କିଟୋନ୍ ଏବଂ କାର୍ବୋକ୍ସିଲିକ୍ ଏସିଡ୍",
      classLevel = 12,
      weightageMarks = 8,
      summary = "Nomenclature, structure of carbonyl group, Aldol condensation, Cannizzaro reaction, Clemmensen reduction, Hell-Volhard-Zelinsky (HVZ) reaction.",
      isCompleted = false,
      progressPercent = 20
    ),

    // Class 12 Mathematics
    Chapter(
      id = "ch_math12_1",
      subjectId = "math_12",
      chapterNumber = 1,
      title = "Matrices and Determinants",
      odiaTitle = "ମାଟ୍ରିକ୍ସ ଓ ଡିଟରମିନାଣ୍ଟ",
      classLevel = 12,
      weightageMarks = 10,
      summary = "Types of matrices, operations, transpose, adjoint, inverse of a square matrix, properties of determinants, solving linear equations by Cramer's rule & Matrix method.",
      isCompleted = true,
      progressPercent = 100
    ),
    Chapter(
      id = "ch_math12_2",
      subjectId = "math_12",
      chapterNumber = 2,
      title = "Continuity and Differentiability",
      odiaTitle = "ଅବିଚ୍ଛିନ୍ନତା ଓ ଅବକଳନୀୟତା",
      classLevel = 12,
      weightageMarks = 10,
      summary = "Continuity of functions, derivative of composite, implicit and parametric functions, logarithmic differentiation, Rolle's and Lagrange's Mean Value Theorems.",
      isCompleted = true,
      progressPercent = 90
    ),
    Chapter(
      id = "ch_math12_3",
      subjectId = "math_12",
      chapterNumber = 3,
      title = "Integrals",
      odiaTitle = "ସମାକଳନ (ଇଣ୍ଟିଗ୍ରାଲ୍ସ)",
      classLevel = 12,
      weightageMarks = 12,
      summary = "Indefinite integrals, integration by substitution, partial fractions, integration by parts, definite integrals and properties, area under simple curves.",
      isCompleted = false,
      progressPercent = 40
    ),

    // Class 12 Biology
    Chapter(
      id = "ch_bio12_1",
      subjectId = "bio_12",
      chapterNumber = 1,
      title = "Sexual Reproduction in Flowering Plants (Botany)",
      odiaTitle = "ପୁଷ୍ପକ ଉଦ୍ଭିଦରେ ଲୈଙ୍ଗିକ ପ୍ରଜନନ",
      classLevel = 12,
      weightageMarks = 7,
      summary = "Structure of flower, microsporogenesis, megasporogenesis, pollination mechanisms, double fertilization, development of endosperm and embryo.",
      isCompleted = true,
      progressPercent = 100
    ),
    Chapter(
      id = "ch_bio12_2",
      subjectId = "bio_12",
      chapterNumber = 2,
      title = "Human Reproduction (Zoology)",
      odiaTitle = "ମାନବ ପ୍ରଜନନ",
      classLevel = 12,
      weightageMarks = 8,
      summary = "Male and female reproductive systems, spermatogenesis, oogenesis, menstrual cycle, fertilization, blastocyst development, implantation and pregnancy.",
      isCompleted = false,
      progressPercent = 50
    ),

    // Class 12 MIL Odia
    Chapter(
      id = "ch_odia12_1",
      subjectId = "odia_12",
      chapterNumber = 1,
      title = "ବନ୍ଦେ ଉତ୍କଳ ଜନନୀ (କାନ୍ତକବି ଲକ୍ଷ୍ମୀକାନ୍ତ ମହାପାତ୍ର)",
      odiaTitle = "ବନ୍ଦେ ଉତ୍କଳ ଜନନୀ",
      classLevel = 12,
      weightageMarks = 6,
      summary = "ଉତ୍କଳ ଜନନୀଙ୍କ ପ୍ରାକୃତିକ ସୌନ୍ଦର୍ଯ୍ୟ, ନଦୀ-ବନ-ପର୍ବତ ମାଳା, ପ୍ରାଚୀନ କୀର୍ତ୍ତି ଓ କବି-ବିଦ୍ୱାନମାନଙ୍କ ଅବଦାନର ଭାବମୟ ବର୍ଣ୍ଣନା ।",
      isCompleted = true,
      progressPercent = 100
    ),

    // Class 12 English
    Chapter(
      id = "ch_eng12_1",
      subjectId = "eng_12",
      chapterNumber = 1,
      title = "On Examinations - Sir Winston S. Churchill",
      odiaTitle = "ଅନ୍ ଏକଜାମିନେସନ୍ସ",
      classLevel = 12,
      weightageMarks = 6,
      summary = "Humorous reflection on Churchill's struggles with examinations, the Harrow school entrance experience, and his perspective on English vs Latin/Greek.",
      isCompleted = true,
      progressPercent = 85
    )
  )

  // Full 12-Section Content for flagship Chapter: Electric Charges and Fields (CHSE Class 12 Physics)
  val flagshipChapterContent = ChapterContent(
    chapterId = "ch_phy12_1",
    overview = ChapterOverview(
      keyConcepts = listOf(
        "Quantization and Conservation of Electric Charge",
        "Coulomb's Law in Vector Form & Superposition Principle",
        "Electric Field and Electric Dipole Moment (p = q × 2a)",
        "Torque on a Dipole in a Uniform Electric Field (τ = p × E)",
        "Electric Flux and Gauss's Law (∮ E·dA = q_enc / ε₀)",
        "Application 1: Field due to Infinitely Long Straight Wire (E = λ / 2πε₀r)",
        "Application 2: Field due to Uniformly Charged Infinite Plane Sheet (E = σ / 2ε₀)",
        "Application 3: Field due to Thin Spherical Shell (Inside = 0, Outside = q / 4πε₀r²)"
      ),
      chseWeightageNotice = "Official CHSE Odisha Exam Weightage: 8 Marks (Usually 1 MCQ + 1 Short Question [2/3M] + 1 Long Derivation [7M] in Group C)",
      examTips = listOf(
        "Draw neat labeled diagrams for Gauss Law Gaussian surfaces - examiners award 2 marks exclusively for correct diagram & vector orientations.",
        "Always write vector notation: F₁₂ = -F₂₁ when proving Coulomb's law follows Newton's 3rd law.",
        "Remember: Electric field inside a conducting charged spherical shell is ALWAYS zero."
      ),
      learningObjectives = listOf(
        "State and prove Gauss's Theorem from Coulomb's Law.",
        "Derive electric field intensity on axial line and equatorial line of an electric dipole.",
        "Solve numericals on charge distributions and force equilibrium."
      )
    ),
    notes = listOf(
      NoteSection(
        heading = "1. Basic Properties of Electric Charges",
        content = "Electric charge is the intrinsic fundamental property of matter. Charges are of two types: Positive and Negative (Benjamin Franklin).",
        bulletPoints = listOf(
          "Quantization of Charge: q = ±ne, where n is an integer and e = 1.6 × 10⁻¹⁹ C.",
          "Additivity: Total charge is the algebraic sum of individual charges.",
          "Conservation: The total electric charge of an isolated system remains constant.",
          "Invariance: Charge is independent of the frame of reference velocity."
        ),
        examTakeaway = "CHSE 1-Mark Definition Question: 'State the law of quantization of electric charge.'"
      ),
      NoteSection(
        heading = "2. Coulomb's Law in Electrostatics",
        content = "The electrostatic force between two stationary point charges is directly proportional to the product of magnitudes of charges and inversely proportional to the square of the distance between them.",
        bulletPoints = listOf(
          "Formula: F = (1 / 4πε₀) × (|q₁q₂| / r²)",
          "Constant: 1 / 4πε₀ = 8.987 × 10⁹ N·m²/C² ≈ 9 × 10⁹ N·m²/C²",
          "Permittivity of free space: ε₀ = 8.854 × 10⁻¹² C²/(N·m²)",
          "In a dielectric medium with relative permittivity εᵣ: F_med = F_air / εᵣ"
        ),
        examTakeaway = "Coulomb's force acts along the line joining the two charges (Central force)."
      ),
      NoteSection(
        heading = "3. Electric Dipole & Field on Axial / Equatorial Lines",
        content = "An electric dipole consists of two equal and opposite point charges (+q and -q) separated by a small distance 2a. Dipole moment p = q × (2a), directed from -q to +q.",
        bulletPoints = listOf(
          "Axial Line (End-on position): E_axial = (1 / 4πε₀) × (2pr / (r² - a²)²) ≈ (2p / 4πε₀r³) for r >> a.",
          "Equatorial Line (Broad-side on position): E_equatorial = (1 / 4πε₀) × (p / (r² + a²)^(3/2)) ≈ (p / 4πε₀r³) for r >> a.",
          "Crucial Relation: E_axial = 2 × E_equatorial at the same large distance r."
        ),
        examTakeaway = "Frequently asked in CHSE Group B (3 Marks) and Group C (7 Marks)."
      ),
      NoteSection(
        heading = "4. Gauss's Theorem and Applications",
        content = "Gauss's theorem states that the total electric flux (Φ) through any closed surface (Gaussian surface) in vacuum is equal to 1/ε₀ times the total net charge enclosed inside it: Φ = ∮ E·dA = q_enc / ε₀.",
        bulletPoints = listOf(
          "Infinitely Long Straight Wire: E = λ / (2πε₀r), where λ = linear charge density.",
          "Uniformly Charged Infinite Plane Sheet: E = σ / (2ε₀), independent of distance r!",
          "Thin Spherical Shell: E(r > R) = q / (4πε₀r²); E(r = R) = σ / ε₀; E(r < R) = 0."
        ),
        examTakeaway = "Gauss's Theorem derivation is one of the top 3 most repeated 7-mark questions in CHSE Board history."
      )
    ),
    textbookPages = listOf(
      TextbookPage(
        pageNumber = 1,
        pageTitle = "CHSE Bureau Physics: Chapter 1 - Static Electricity & Field",
        content = "According to the Odisha State Bureau of Textbook Preparation and Production syllabus, electrostatic phenomena form the baseline of electrodynamics. When bodies are rubbed together, transfer of electrons creates static electrification. Glass rod rubbed with silk gains positive charge, while ebonite rod rubbed with cat's fur gains negative charge.\n\nGold Leaf Electroscope (GLE) is used to detect presence and nature of electric charge. Coulomb's torsion balance experimentally verified the inverse square law for electrostatic forces in 1785.",
        callout = "Odisha Board Note: SI unit of electric charge is Coulomb (C). In CGS electrostatic system, 1 Coulomb = 3 × 10⁹ statC (esu of charge)."
      ),
      TextbookPage(
        pageNumber = 2,
        pageTitle = "Gauss's Law & Gaussian Surface Rules",
        content = "To apply Gauss's Theorem effectively, we choose an imaginary closed surface called a Gaussian surface. The surface must satisfy:\n1. Symmetry: The electric field E should have a constant magnitude over the surface or parts of it.\n2. Angle: The angle between E and area vector dA should be either 0° (cos 0° = 1) or 90° (cos 90° = 0).\n3. Charge Location: The Gaussian surface must not pass through discrete point charges.\n\nFlux Φ = ∫ E·dA = ∫ E dA cos θ. For closed surface ∮ E·dA = q_enclosed / ε₀.",
        callout = "Bureau Tip: For line charge, choose a coaxial cylinder; for plane sheet, choose a pillbox cylinder."
      )
    ),
    definitions = listOf(
      DefinitionItem(
        term = "Electric Dipole Moment",
        definition = "The vector product of magnitude of either charge and the separation vector between two equal and opposite charges. It is directed from negative to positive charge.",
        marks = 1,
        example = "p = q × 2a. SI unit is Coulomb-meter (C·m)."
      ),
      DefinitionItem(
        term = "Electric Flux",
        definition = "The total number of electric lines of force passing normally through a given surface area placed in an electric field. Mathematically, Φ = ∮ E·dA.",
        marks = 1,
        example = "SI unit is N·m²/C or Volt-meter (V·m). It is a scalar quantity."
      ),
      DefinitionItem(
        term = "Dielectric Constant (Relative Permittivity)",
        definition = "The ratio of the permittivity of the medium to the permittivity of free space, or the ratio of electrostatic force between two charges in vacuum to that in the medium.",
        marks = 2,
        example = "εᵣ = ε / ε₀ = F_vacuum / F_medium. Dimensionless quantity."
      ),
      DefinitionItem(
        term = "Linear Charge Density (λ)",
        definition = "Charge per unit length along a line conductor: λ = dq / dl. SI unit is C/m.",
        marks = 1,
        example = "Used in calculating field due to charged infinite wire."
      )
    ),
    formulas = listOf(
      FormulaItem(
        name = "Coulomb's Law",
        formula = "F = (1 / 4πε₀) · (q₁q₂ / r²)",
        siUnit = "Newton (N)",
        variables = "q₁, q₂ = charges (C), r = distance (m), ε₀ = 8.854 × 10⁻¹² C²/(N·m²)",
        application = "Used to compute mutual electrostatic force between point charges."
      ),
      FormulaItem(
        name = "Electric Field on Axial Line of Dipole",
        formula = "E_axial = (1 / 4πε₀) · (2pr / (r² - a²)²) ≈ 2p / (4πε₀r³)",
        siUnit = "N/C or V/m",
        variables = "p = dipole moment (q·2a), r = distance from center, a = semi-dipole length",
        application = "Short dipole approximation when r >> a."
      ),
      FormulaItem(
        name = "Electric Field on Equatorial Line",
        formula = "E_eq = (1 / 4πε₀) · (p / (r² + a²)^(3/2)) ≈ p / (4πε₀r³)",
        siUnit = "N/C or V/m",
        variables = "p = dipole moment, r = distance along perpendicular bisector",
        application = "Points in opposite direction to dipole moment vector p."
      ),
      FormulaItem(
        name = "Torque on Dipole in Uniform Field",
        formula = "τ = p × E = p E sin θ",
        siUnit = "N·m",
        variables = "p = dipole moment, E = electric field, θ = angle between p and E",
        application = "Max torque when θ = 90°; stable equilibrium when θ = 0°."
      ),
      FormulaItem(
        name = "Gauss's Law Equation",
        formula = "Φ = ∮ E · dA = q_enc / ε₀",
        siUnit = "N·m²/C or V·m",
        variables = "E = field vector, dA = normal area vector, q_enc = net enclosed charge",
        application = "Fundamental Maxwell's equation for electrostatic fields."
      ),
      FormulaItem(
        name = "Field due to Infinite Line Charge",
        formula = "E = λ / (2πε₀r)",
        siUnit = "N/C",
        variables = "λ = linear charge density (C/m), r = radial distance (m)",
        application = "Field varies inversely with distance (E ∝ 1/r)."
      )
    ),
    diagrams = listOf(
      DiagramItem(
        title = "Electric Lines of Force for Unlike & Like Charges",
        description = "Field lines originate on positive charges and terminate on negative charges. For two positive charges, a neutral point (E=0) is formed at the center.",
        keyLabels = listOf("Positive Charge Source", "Negative Charge Sink", "Neutral Point N", "Perpendicular surface emergence"),
        examImportance = "Asked in 2-mark question with diagram.",
        drawingTip = "Ensure field lines never cross each other because at the intersection point there would be two tangents, meaning two directions of field at a single point (physically impossible)."
      ),
      DiagramItem(
        title = "Cylindrical Gaussian Surface for Infinite Line Charge",
        description = "Coaxial cylinder of radius r and length l around a uniformly charged wire with linear density λ.",
        keyLabels = listOf("Charged Wire (λ)", "Curved Cylindrical Surface (cos 0°)", "Two Flat Circular End Caps (cos 90° -> Flux = 0)", "Electric Field Vector E"),
        examImportance = "Essential in 7-mark derivation.",
        drawingTip = "Clearly indicate that flux through both circular flat ends is zero because E is perpendicular to the area vector (E · n̂ = 0)."
      ),
      DiagramItem(
        title = "Electric Dipole on Axial and Equatorial Points",
        description = "Shows +q and -q separated by 2a, with resolution of components E₊ and E₋ along equatorial perpendicular line.",
        keyLabels = listOf("-q and +q charges", "Separation 2a", "Center O", "E_+ sin θ and E_- sin θ cancelling vertically", "2 E cos θ adding horizontally"),
        examImportance = "Must be drawn clearly in long answer derivations.",
        drawingTip = "Use arrows on vector components to prove why sine components cancel and cosine components add up."
      )
    ),
    pyqs = listOf(
      PyqItem(
        year = 2024,
        questionText = "State Gauss's Theorem in electrostatics. Apply it to find the electric field intensity at a point due to an infinitely long straight uniformly charged wire.",
        marks = 7,
        groupName = "Group C (Long Answer)",
        solutionSteps = listOf(
          "Statement (2 Marks): Total electric flux through any closed Gaussian surface in vacuum equals 1/ε₀ times net enclosed charge: ∮ E·dA = q_enc / ε₀.",
          "Diagram (1 Mark): Draw infinitely long straight wire with linear density λ. Enclose a portion of length l with coaxial cylinder of radius r.",
          "Flux computation (2 Marks): Total flux Φ = Φ_curved + Φ_flat1 + Φ_flat2. Since E ⟂ flat faces, flux through ends = 0. Through curved surface: ∮ E·dA = E × (2πrl).",
          "Applying Gauss's Law (2 Marks): E × (2πrl) = q_enc / ε₀ = (λl) / ε₀. Hence, E = λ / (2πε₀r). (Direction radially outwards if λ > 0)."
        )
      ),
      PyqItem(
        year = 2023,
        questionText = "Define electric dipole moment. Derive an expression for electric field intensity at any point on the axial line of an electric dipole.",
        marks = 7,
        groupName = "Group C (Long Answer)",
        solutionSteps = listOf(
          "Definition (1 Mark): Vector quantity p = q × 2a directed from -q to +q.",
          "Diagram & Setup (2 Marks): Point P at distance r from dipole center O. Distance of P from +q is (r - a); distance from -q is (r + a).",
          "Formulas (2 Marks): E₊ = (1/4πε₀) · q / (r - a)² (along OP); E₋ = (1/4πε₀) · q / (r + a)² (along PO).",
          "Resultant (2 Marks): E = E₊ - E₋ = (q/4πε₀) [ 1/(r-a)² - 1/(r+a)² ] = (1/4πε₀) · (4ar·q) / (r² - a²)² = (1/4πε₀) · (2pr) / (r² - a²)². For r >> a, E ≈ 2p / (4πε₀r³)."
        )
      ),
      PyqItem(
        year = 2022,
        questionText = "Two point charges +4μC and +1μC are separated by a distance of 30 cm in air. At what point on the line joining them is the electric field zero?",
        marks = 3,
        groupName = "Group B (Short Answer / Numerical)",
        solutionSteps = listOf(
          "Let point P be at distance x from +4μC charge. Therefore distance from +1μC is (0.30 - x) m.",
          "At point P, E₁ = E₂ => (1/4πε₀) × (4 × 10⁻⁶ / x²) = (1/4πε₀) × (1 × 10⁻⁶ / (0.30 - x)²).",
          "Simplifying: 4 / x² = 1 / (0.30 - x)² => Take square root: 2 / x = 1 / (0.30 - x).",
          "Cross-multiplying: 2(0.30 - x) = x => 0.60 - 2x = x => 3x = 0.60 => x = 0.20 m = 20 cm.",
          "Final Answer: The electric field is zero at a distance of 20 cm from the +4μC charge."
        )
      ),
      PyqItem(
        year = 2020,
        questionText = "What is the SI unit and dimensional formula of electric flux?",
        marks = 1,
        groupName = "Group A (VSA)",
        solutionSteps = listOf(
          "SI Unit: N·m²/C or Volt-meter (V·m).",
          "Dimensional formula: [M L³ T⁻³ A⁻¹]."
        )
      )
    ),
    mcqs = listOf(
      McqItem(
        id = "mcq_1",
        question = "What is the value of electric field intensity inside a uniformly charged conducting spherical shell?",
        options = listOf("Infinite", "Zero", "q / (4πε₀R²)", "σ / ε₀"),
        correctIndex = 1,
        explanation = "According to Gauss's theorem, inside a charged conducting spherical shell, the enclosed charge q_enc = 0. Therefore, ∮ E·dA = 0, which implies E = 0 everywhere inside.",
        topic = "Gauss Law"
      ),
      McqItem(
        id = "mcq_2",
        question = "An electric dipole of dipole moment 'p' is placed in a uniform electric field 'E'. The torque acting on it is given by:",
        options = listOf("p · E", "p × E", "p + E", "p / E"),
        correctIndex = 1,
        explanation = "The torque on an electric dipole in a uniform electric field is τ = p × E = p E sin θ, where θ is the angle between the dipole moment and field vector.",
        topic = "Electric Dipole"
      ),
      McqItem(
        id = "mcq_3",
        question = "Two charges placed in air repel each other with force F. When placed in water (dielectric constant K = 80) at the same distance, the force becomes:",
        options = listOf("80 F", "F", "F / 80", "F / 160"),
        correctIndex = 2,
        explanation = "The electrostatic force in a medium of dielectric constant K is F_med = F_air / K. For water K = 80, so F_med = F / 80.",
        topic = "Coulomb's Law"
      ),
      McqItem(
        id = "mcq_4",
        question = "Electric flux through a closed surface containing dipole charges (+q, -q) is:",
        options = listOf("2q / ε₀", "q / ε₀", "Zero", "-q / ε₀"),
        correctIndex = 2,
        explanation = "Net charge enclosed q_net = (+q) + (-q) = 0. By Gauss's Law, Φ = q_net / ε₀ = 0.",
        topic = "Electric Flux"
      ),
      McqItem(
        id = "mcq_5",
        question = "The ratio of electric field at axial point to equatorial point of a short dipole at equal large distance 'r' is:",
        options = listOf("1 : 1", "2 : 1", "1 : 2", "4 : 1"),
        correctIndex = 1,
        explanation = "E_axial = 2p / (4πε₀r³) and E_equatorial = p / (4πε₀r³). Therefore E_axial / E_equatorial = 2 : 1.",
        topic = "Electric Dipole"
      )
    ),
    shortAnswers = listOf(
      ShortQaItem(
        question = "State the principle of superposition of electrostatic forces.",
        marks = 2,
        modelAnswer = "The principle of superposition states that when a number of charges are interacting, the total electrostatic force on a given charge is the vector sum of the forces exerted on it by all other individual charges taken one at a time. The force between any two charges remains unaffected by the presence of other charges.",
        keyPoints = listOf("Vector sum of individual forces: F = F₁ + F₂ + ... + F_n", "Pair-wise force is independent of third charges")
      ),
      ShortQaItem(
        question = "Why do electric field lines never intersect each other?",
        marks = 2,
        modelAnswer = "If two electric field lines were to intersect at a point, then at that point of intersection, two tangents could be drawn to the two lines. This would mean that at that single point, the electric field has two different directions, which is physically impossible. Hence, electric lines of force never intersect.",
        keyPoints = listOf("Tangent gives direction of electric field", "Intersection implies two directions at one location which is impossible")
      )
    ),
    longAnswers = listOf(
      LongQaItem(
        question = "State Gauss's Law. Using this law, obtain an expression for electric field intensity due to a uniformly charged thin spherical shell at: (i) a point outside the shell, (ii) a point on the surface, and (iii) a point inside the shell.",
        marks = 7,
        structuredAnswer = "1. Statement of Gauss's Law:\nGauss's law states that the total outward electric flux through any closed hypothetical Gaussian surface in vacuum is equal to 1/ε₀ times the total charge enclosed by that surface: ∮ E·dA = q_enc / ε₀.\n\n2. Spherical Shell Considerations:\nLet a thin spherical shell of radius R carry a total uniform charge q with surface charge density σ = q / (4πR²).\n\n(i) At a point P outside the shell (r > R):\nConstruct a concentric spherical Gaussian surface of radius r passing through P. By spherical symmetry, electric field E is directed radially outwards and has constant magnitude at all points on this surface.\n∮ E·dA = E ∮ dA = E × (4πr²).\nAccording to Gauss's law:\nE × (4πr²) = q / ε₀\n=> E = (1 / 4πε₀) · (q / r²) (directed radially outwards).\nConclusion: For points outside, the shell behaves as if its entire charge were concentrated at its center.\n\n(ii) At a point on the surface (r = R):\nSubstituting r = R:\nE_surface = (1 / 4πε₀) · (q / R²).\nIn terms of surface density σ: E_surface = σ / ε₀.\n\n(iii) At a point inside the shell (r < R):\nDraw a concentric spherical Gaussian surface of radius r < R. Since all charge resides on the outer surface of the shell, the charge enclosed inside this inner surface is zero (q_enc = 0).\nTherefore: ∮ E·dA = E × (4πr²) = 0 / ε₀ = 0\n=> E_inside = 0.\n\nConclusion: The electric field inside a uniformly charged thin spherical shell is identically zero everywhere.",
        stepBreakdown = listOf(
          "Statement of Gauss's Law: 1 Mark",
          "Neat diagram with Gaussian surfaces: 1 Mark",
          "Derivation for outer point (r > R): 2 Marks",
          "Derivation for surface point (r = R): 1 Mark",
          "Derivation for inner point (r < R): 2 Marks"
        ),
        diagramRequired = true,
        markingScheme = "CHSE 7-Mark Distribution: Statement 1M + Diagram 1M + Derivations 5M = Total 7M"
      )
    ),
    doubtPrompts = listOf(
      "Explain Gauss's law with Odia explanation (ଓଡ଼ିଆରେ ବୁଝାନ୍ତୁ)",
      "Why is electric field zero inside a charged spherical conductor?",
      "Derive expression for torque on dipole placed in uniform field",
      "How to solve Coulomb's law equilibrium problem between 2 charges?"
    )
  )

  // Curated Video Lectures (CHSE Odisha syllabus)
  val videoLectures: List<VideoLecture> = listOf(
    VideoLecture(
      id = "vid_phy_1",
      chapterId = "ch_phy12_1",
      subjectId = "phy_12",
      title = "Electric Charges & Gauss's Law Masterclass - CHSE Board Special",
      tutorName = "Prof. S. K. Mohanty (CHSE Physics Specialist)",
      duration = "45:30",
      videoUrl = "https://www.youtube.com/watch?v=chse_physics_lecture1",
      timestamps = listOf(
        "00:00" to "Introduction & CHSE Syllabus Blueprint",
        "05:20" to "Quantization & Coulomb's Law in Vector Form",
        "18:40" to "Electric Dipole & Torque Derivation",
        "31:10" to "Gauss Theorem & 7-Mark Long Question Proof",
        "41:00" to "Previous 5-Year PYQ Discussion"
      ),
      keySummary = "Comprehensive review of Electric Charges and Fields covering Coulomb's vector law, electric dipoles, Gauss's theorem proof, and the 3 core applications required for CHSE Odisha 2025/2026 Board Exams."
    ),
    VideoLecture(
      id = "vid_chem_1",
      chapterId = "ch_chem12_1",
      subjectId = "chem_12",
      title = "Solutions & Colligative Properties - Complete One-Shot",
      tutorName = "Dr. P. R. Panda (Senior Chemistry Lecturer)",
      duration = "52:15",
      videoUrl = "https://www.youtube.com/watch?v=chse_chem_lecture1",
      timestamps = listOf(
        "00:00" to "Raoult's Law & Ideal Solutions",
        "14:10" to "Elevation in Boiling Point Derivation",
        "28:00" to "Depression in Freezing Point & Cryoscopic Constant",
        "39:45" to "Osmotic Pressure & Van't Hoff Factor",
        "48:00" to "Numerical Problem Solving from CHSE Bureau Book"
      ),
      keySummary = "Master all 4 colligative properties with step-by-step numerical solving from the official Bureau Chemistry textbook."
    ),
    VideoLecture(
      id = "vid_math_1",
      chapterId = "ch_math12_1",
      subjectId = "math_12",
      title = "Matrices & Determinants - Cramer's Rule & Matrix Inverse",
      tutorName = "Er. A. K. Behera (Elements of Mathematics Expert)",
      duration = "38:40",
      videoUrl = "https://www.youtube.com/watch?v=chse_math_lecture1",
      timestamps = listOf(
        "00:00" to "Properties of Determinants & Matrix Operations",
        "12:30" to "Adjoint and Inverse of 3x3 Matrix",
        "25:15" to "Solving System of Linear Equations (Matrix Method)",
        "34:00" to "CHSE 6-mark guaranteed questions"
      ),
      keySummary = "Step-by-step masterclass on solving 3-variable linear equations using matrix inversion and Cramer's rule, a guaranteed 6-mark section in CHSE mathematics."
    )
  )

  // Mock Tests
  val mockTests: List<MockTest> = listOf(
    MockTest(
      id = "test_1",
      title = "CHSE Physics Unit 1: Electrostatics Chapter Test",
      subjectName = "Physics",
      classLevel = 12,
      testType = "Chapter Test",
      durationMinutes = 30,
      totalMarks = 20,
      questions = flagshipChapterContent.mcqs,
      isCompleted = true,
      lastScore = 18,
      lastTakenTime = "Yesterday"
    ),
    MockTest(
      id = "test_2",
      title = "CHSE Odisha Annual Science Mock Examination 2026 - Paper I",
      subjectName = "Physics",
      classLevel = 12,
      testType = "Annual Mock",
      durationMinutes = 60,
      totalMarks = 50,
      questions = flagshipChapterContent.mcqs + listOf(
        McqItem(
          id = "mcq_6",
          question = "Dimensional formula of electrical permittivity (ε₀) is:",
          options = listOf("[M⁻¹ L⁻³ T⁴ A²]", "[M L³ T⁻³ A⁻¹]", "[M L² T⁻³ A⁻²]", "[M⁻¹ L² T⁻² A]"),
          correctIndex = 0,
          explanation = "From Coulomb's law ε₀ = q₁q₂ / (4πFr²). Dimensions = [A² T²] / ([M L T⁻²] [L²]) = [M⁻¹ L⁻³ T⁴ A²].",
          topic = "Units"
        ),
        McqItem(
          id = "mcq_7",
          question = "An electric dipole of moment 'p' placed in a non-uniform electric field experiences:",
          options = listOf("Only torque", "Only net force", "Both net force and torque", "Neither force nor torque"),
          correctIndex = 2,
          explanation = "In a non-uniform field, forces on +q and -q are unequal and non-collinear, causing both net translational force and rotational torque.",
          topic = "Electric Dipole"
        )
      )
    ),
    MockTest(
      id = "test_3",
      title = "CHSE Chemistry Half-Yearly Practice Test",
      subjectName = "Chemistry",
      classLevel = 12,
      testType = "Half-Yearly Mock",
      durationMinutes = 45,
      totalMarks = 35,
      questions = listOf(
        McqItem(
          id = "chem_q1",
          question = "Which of the following is a colligative property?",
          options = listOf("Viscosity", "Surface tension", "Osmotic pressure", "Refractive index"),
          correctIndex = 2,
          explanation = "Colligative properties depend only on the number of solute particles, not their chemical nature. Osmotic pressure is colligative.",
          topic = "Solutions"
        ),
        McqItem(
          id = "chem_q2",
          question = "The unit of rate constant for a zero order reaction is:",
          options = listOf("s⁻¹", "mol L⁻¹ s⁻¹", "L mol⁻¹ s⁻¹", "mol² L⁻² s⁻¹"),
          correctIndex = 1,
          explanation = "Rate = k [A]⁰ => k = Rate = mol L⁻¹ s⁻¹.",
          topic = "Chemical Kinetics"
        ),
        McqItem(
          id = "chem_q3",
          question = "In Aldol condensation, the reacting aldehyde must contain at least one:",
          options = listOf("α-hydrogen", "β-hydrogen", "Double bond", "Halogen atom"),
          correctIndex = 0,
          explanation = "Aldol condensation requires presence of at least one acidic α-hydrogen atom to form carbanion intermediate.",
          topic = "Organic Chemistry"
        )
      )
    ),
    MockTest(
      id = "test_4",
      title = "Elements of Mathematics Unit Test: Calculus Fundamentals",
      subjectName = "Mathematics",
      classLevel = 12,
      testType = "Unit Test",
      durationMinutes = 40,
      totalMarks = 25,
      questions = listOf(
        McqItem(
          id = "math_q1",
          question = "If A is a square matrix of order 3 and |A| = 5, then |adj A| is equal to:",
          options = listOf("5", "25", "125", "1/5"),
          correctIndex = 1,
          explanation = "Formula: |adj A| = |A|^(n - 1). For n = 3, |adj A| = 5^(3 - 1) = 5² = 25.",
          topic = "Matrices"
        ),
        McqItem(
          id = "math_q2",
          question = "Derivative of sin⁻¹(x) with respect to cos⁻¹(√(1 - x²)) is:",
          options = listOf("1", "-1", "1/2", "0"),
          correctIndex = 0,
          explanation = "Let u = sin⁻¹(x) and v = cos⁻¹(√(1 - x²)). Put x = sin θ => v = cos⁻¹(cos θ) = θ = sin⁻¹(x) = u. So du/dv = 1.",
          topic = "Differentiation"
        )
      )
    )
  )

  // Previous Year Papers
  val pyqPapers: List<PyqPaper> = listOf(
    PyqPaper(
      id = "pyq_phy_2024",
      title = "CHSE Odisha Annual Higher Secondary Examination 2024 - Physics",
      subjectId = "phy_12",
      subjectName = "Physics",
      year = 2024,
      duration = "3 Hours",
      totalMarks = 70,
      questions = flagshipChapterContent.pyqs
    ),
    PyqPaper(
      id = "pyq_phy_2023",
      title = "CHSE Odisha Annual Higher Secondary Examination 2023 - Physics",
      subjectId = "phy_12",
      subjectName = "Physics",
      year = 2023,
      duration = "3 Hours",
      totalMarks = 70,
      questions = flagshipChapterContent.pyqs.filter { it.year == 2023 || it.year == 2022 }
    ),
    PyqPaper(
      id = "pyq_chem_2024",
      title = "CHSE Odisha Annual Higher Secondary Examination 2024 - Chemistry",
      subjectId = "chem_12",
      subjectName = "Chemistry",
      year = 2024,
      duration = "3 Hours",
      totalMarks = 70,
      questions = listOf(
        PyqItem(
          year = 2024,
          questionText = "State Kohlrausch's law of independent migration of ions. How is it used to calculate molar conductivity of weak electrolyte at infinite dilution?",
          marks = 7,
          groupName = "Group C",
          solutionSteps = listOf(
            "State law (2M): Limiting molar conductivity of an electrolyte can be represented as the sum of individual contributions of anion and cation: Λ°ₘ = ν₊λ°₊ + ν₋λ°₋.",
            "Application (3M): Show calculation of Λ°(CH₃COOH) = Λ°(CH₃COONa) + Λ°(HCl) - Λ°(NaCl).",
            "Formula for degree of dissociation α = Λₘ / Λ°ₘ (2M)."
          )
        ),
        PyqItem(
          year = 2024,
          questionText = "What is Cannizzaro's reaction? Write the chemical equation with mechanism.",
          marks = 3,
          groupName = "Group B",
          solutionSteps = listOf(
            "Definition (1M): Aldehydes having no α-hydrogen (e.g., HCHO, C₆H₅CHO) undergo self-oxidation and reduction in conc. NaOH to form an alcohol and salt of carboxylic acid.",
            "Equation (2M): 2 HCHO + conc. NaOH -> CH₃OH + HCOONa."
          )
        )
      )
    ),
    PyqPaper(
      id = "pyq_math_2024",
      title = "CHSE Odisha Annual Higher Secondary Examination 2024 - Mathematics",
      subjectId = "math_12",
      subjectName = "Mathematics",
      year = 2024,
      duration = "3 Hours",
      totalMarks = 100,
      questions = listOf(
        PyqItem(
          year = 2024,
          questionText = "Solve the following system of linear equations by Matrix method: x - y + 2z = 7, 3x + 4y - 5z = -5, 2x - y + 3z = 12.",
          marks = 6,
          groupName = "Group C",
          solutionSteps = listOf(
            "Write in AX = B form where A is coefficient matrix, X is column vector [x y z]ᵀ, B is [7 -5 12]ᵀ.",
            "Calculate determinant |A| = 4 ≠ 0, hence A is invertible.",
            "Compute cofactor matrix and Adjoint(A).",
            "X = A⁻¹ B = (1 / |A|) Adj(A) B.",
            "Obtain solutions: x = 2, y = 1, z = 3."
          )
        )
      )
    )
  )

  // Daily Study Goals
  val defaultGoals: List<StudyGoal> = listOf(
    StudyGoal(id = "g1", title = "Revise Electric Dipole Derivations", subject = "Physics", targetMinutes = 45, isDone = true),
    StudyGoal(id = "g2", title = "Solve 20 MCQs on Solutions & Raoult's Law", subject = "Chemistry", targetMinutes = 30, isDone = true),
    StudyGoal(id = "g3", title = "Practice Integration by Parts exercises", subject = "Mathematics", targetMinutes = 60, isDone = false),
    StudyGoal(id = "g4", title = "Memorize Flower Reproductive Diagram Labels", subject = "Biology", targetMinutes = 25, isDone = false),
    StudyGoal(id = "g5", title = "Write Report on College Annual Function", subject = "English", targetMinutes = 20, isDone = false)
  )

  // Timetable
  val timetable: List<TimetableItem> = listOf(
    TimetableItem("Monday", "06:00 AM - 07:30 AM", "Physics", "Gauss's Theorem and Applications"),
    TimetableItem("Monday", "05:00 PM - 06:30 PM", "Chemistry", "Electrochemistry Nernst Equation"),
    TimetableItem("Tuesday", "06:00 AM - 07:30 AM", "Mathematics", "Definite Integrals Properties"),
    TimetableItem("Tuesday", "05:00 PM - 06:30 PM", "Biology", "Sexual Reproduction in Angiosperms"),
    TimetableItem("Wednesday", "06:00 AM - 07:30 AM", "Physics", "Ray Optics - Prism & Lens Maker"),
    TimetableItem("Thursday", "05:00 PM - 06:30 PM", "MIL Odia", "ବନ୍ଦେ ଉତ୍କଳ ଜନନୀ ଭାବାର୍ଥ ଓ ବ୍ୟାକରଣ")
  )

  // Official Announcements
  val announcements: List<ChseAnnouncement> = listOf(
    ChseAnnouncement(
      id = "ann_1",
      title = "CHSE Odisha Annual +2 Science Examination 2026 Schedule Notification",
      date = "15 Oct 2026",
      category = "Exam Schedule",
      description = "The Council of Higher Secondary Education, Odisha has officially published the tentative timetable for +2 Annual Science Exams starting from mid-February. Theory exams will carry 70 marks for lab subjects and practicals 30 marks.",
      isImportant = true
    ),
    ChseAnnouncement(
      id = "ann_2",
      title = "Revised Model Question Papers and Blueprint Released for Physics & Chemistry",
      date = "02 Nov 2026",
      category = "Syllabus Update",
      description = "The Odisha State Textbook Bureau has updated the Chapter-wise weightage and question format. Group A will consist of 50% objective MCQs and 1-mark one-line answers, Group B contains 2/3 mark short questions, and Group C contains 7-mark long questions.",
      isImportant = true
    ),
    ChseAnnouncement(
      id = "ann_3",
      title = "Practical Examination Guidelines for Higher Secondary Science Stream",
      date = "28 Oct 2026",
      category = "Practical Exam",
      description = "Internal and external practical assessment schedules for Physics, Chemistry, and Biology laboratories have been notified. Practical records must be duly signed by respective college demonstrators.",
      isImportant = false
    )
  )
}
